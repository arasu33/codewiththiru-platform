package com.codewiththiru.billing.subscription

sealed class SubscriptionEligibility {
    object EligibleForTrial : SubscriptionEligibility()
    object EligibleForIntroPricing : SubscriptionEligibility()
    object StandardPricing : SubscriptionEligibility()
    object Ineligible : SubscriptionEligibility()
}
