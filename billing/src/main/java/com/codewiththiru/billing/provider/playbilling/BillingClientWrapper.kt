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

class BillingClientWrapper(
    context: Context,
    private val connectionState: MutableStateFlow<BillingState>
) {
    private val purchasesUpdatedListener = com.android.billingclient.api.PurchasesUpdatedListener { result, purchases ->
        // To be implemented in PurchaseEngine
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

        val result = suspendCoroutine<PlayBillingResult> { continuation ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: PlayBillingResult) {
                    continuation.resume(billingResult)
                }

                override fun onBillingServiceDisconnected() {
                    connectionState.value = BillingState.Disconnected
                    // Disconnected - handled by ConnectionManager usually
                }
            })
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
