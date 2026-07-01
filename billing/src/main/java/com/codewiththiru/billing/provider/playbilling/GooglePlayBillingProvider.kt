package com.codewiththiru.billing.provider.playbilling

import android.content.Context
import com.codewiththiru.billing.api.BillingResult
import com.codewiththiru.billing.api.BillingState
import com.codewiththiru.billing.config.BillingConfig
import com.codewiththiru.billing.provider.BillingProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GooglePlayBillingProvider(
    private val context: Context,
    private val config: BillingConfig
) : BillingProvider {

    private val _connectionState = MutableStateFlow<BillingState>(BillingState.Disconnected)
    override val connectionState: StateFlow<BillingState> = _connectionState.asStateFlow()

    // Using a wrapper to encapsulate the Play BillingClient
    private val billingClientWrapper = BillingClientWrapper(context, _connectionState, config)

    override suspend fun connect(): BillingResult<Unit> {
        _connectionState.value = BillingState.Connecting
        return billingClientWrapper.connectToPlayBilling(config.retryPolicy)
    }

    override suspend fun disconnect() {
        billingClientWrapper.disconnect()
        _connectionState.value = BillingState.Disconnected
    }
}
