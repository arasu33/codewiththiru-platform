package com.codewiththiru.billing.api

import kotlinx.coroutines.flow.StateFlow

interface BillingManager {
    val connectionState: StateFlow<BillingState>

    suspend fun initialize(): BillingResult<Unit>

    suspend fun disconnect()
}
