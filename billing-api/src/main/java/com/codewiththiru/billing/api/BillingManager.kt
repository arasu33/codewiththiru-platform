package com.codewiththiru.billing.api

import com.codewiththiru.billing.api.BillingResult
import com.codewiththiru.billing.api.BillingState
import kotlinx.coroutines.flow.StateFlow

interface BillingManager {
    val connectionState: StateFlow<BillingState>
    
    suspend fun initialize(): BillingResult<Unit>
    suspend fun disconnect()
}
