package com.codewiththiru.billing.subscription

import kotlinx.coroutines.flow.StateFlow

interface SubscriptionManager {
    val currentSubscriptions: StateFlow<List<SubscriptionStatus>>
    
    suspend fun checkEligibility(tier: SubscriptionTier): SubscriptionEligibility
    suspend fun upgradeOrDowngrade(
        oldPlanId: String, 
        newPlanId: String,
        replacementMode: Int = com.android.billingclient.api.BillingFlowParams.SubscriptionUpdateParams.ReplacementMode.CHARGE_PRORATED_PRICE
    )
}
