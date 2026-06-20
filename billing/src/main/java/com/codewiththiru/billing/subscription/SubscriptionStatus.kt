package com.codewiththiru.billing.subscription

data class SubscriptionStatus(
    val productId: String,
    val tier: SubscriptionTier,
    val isActive: Boolean,
    val isAutoRenewing: Boolean,
    val expiryDateMillis: Long
)
