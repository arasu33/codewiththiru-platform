package com.codewiththiru.billing.subscription

import kotlinx.coroutines.flow.StateFlow

interface SubscriptionManager {
    val currentSubscriptions: StateFlow<List<SubscriptionStatus>>
    
    suspend fun checkEligibility(tier: SubscriptionTier): SubscriptionEligibility
    suspend fun upgradeOrDowngrade(oldPlanId: String, newPlanId: String)
}
