package com.codewiththiru.billing.entitlement

import kotlinx.coroutines.flow.StateFlow

interface EntitlementManager {
    val entitlements: StateFlow<Map<String, Entitlement>>
    
    suspend fun hasEntitlement(entitlementId: String): Boolean
    suspend fun isFeatureEnabled(featureGate: FeatureGate): Boolean
    suspend fun refreshEntitlements()
}
