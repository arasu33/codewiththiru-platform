package com.codewiththiru.billing.provider

import com.codewiththiru.billing.api.BillingResult
import com.codewiththiru.billing.api.BillingState
import kotlinx.coroutines.flow.StateFlow

interface BillingProvider {
    val connectionState: StateFlow<BillingState>
    
    suspend fun connect(): BillingResult<Unit>
    suspend fun disconnect()
}
