package com.codewiththiru.billing.provider.playbilling

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult as PlayBillingResult
import com.codewiththiru.billing.api.BillingError
import com.codewiththiru.billing.api.BillingResult
import com.codewiththiru.billing.api.BillingState
import com.codewiththiru.billing.config.RetryPolicy
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class BillingClientWrapper(
    context: Context,
    private val connectionState: MutableStateFlow<BillingState>,
    private val config: com.codewiththiru.billing.config.BillingConfig
) {
    private val receiptVerifier = com.codewiththiru.billing.fraud.ReceiptVerifier(config.base64PublicKey)

    private val _purchases = MutableSharedFlow<List<com.android.billingclient.api.Purchase>>(extraBufferCapacity = 1)
    val purchases = _purchases.asSharedFlow()

    private val purchasesUpdatedListener = com.android.billingclient.api.PurchasesUpdatedListener { result, purchasesList ->
        if (result.responseCode == BillingClient.BillingResponseCode.OK && purchasesList != null) {
            val validPurchases = purchasesList.filter { purchase ->
                receiptVerifier.verifyReceipt(purchase.originalJson, purchase.signature)
            }
            _purchases.tryEmit(validPurchases)
        } else {
            // Error handled by connection state or another mechanism if needed
            if (result.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) {
                connectionState.value = BillingState.Error(BillingError(result.responseCode, result.debugMessage))
            }
        }
    }

    private var billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    suspend fun connectToPlayBilling(retryPolicy: RetryPolicy): BillingResult<Unit> {
        return connectWithRetry(retryPolicy, 0)
    }

    private suspend fun connectWithRetry(retryPolicy: RetryPolicy, currentAttempt: Int): BillingResult<Unit> {
        if (billingClient.isReady) {
            connectionState.value = BillingState.Connected
            return BillingResult.Success(Unit)
        }

        val result = kotlinx.coroutines.suspendCancellableCoroutine<PlayBillingResult> { continuation ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: PlayBillingResult) {
                    if (continuation.isActive) {
                        continuation.resume(billingResult)
                    }
                }

                override fun onBillingServiceDisconnected() {
                    connectionState.value = BillingState.Disconnected
                    // Disconnected - handled by ConnectionManager usually
                }
            })
            
            continuation.invokeOnCancellation {
                disconnect()
            }
        }

        if (result.responseCode == BillingClient.BillingResponseCode.OK) {
            connectionState.value = BillingState.Connected
            return BillingResult.Success(Unit)
        } else {
            if (currentAttempt < retryPolicy.maxRetries) {
                val delayMs = retryPolicy.initialDelayMs * Math.pow(retryPolicy.backoffMultiplier.toDouble(), currentAttempt.toDouble()).toLong()
                delay(delayMs)
                return connectWithRetry(retryPolicy, currentAttempt + 1)
            } else {
                val error = BillingError(result.responseCode, result.debugMessage)
                connectionState.value = BillingState.Error(error)
                return BillingResult.Failure(error)
            }
        }
    }

    fun disconnect() {
        if (billingClient.isReady) {
            billingClient.endConnection()
        }
    }
}
