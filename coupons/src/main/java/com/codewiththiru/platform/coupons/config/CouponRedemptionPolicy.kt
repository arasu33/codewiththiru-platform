package com.codewiththiru.platform.coupons.config

data class CouponRedemptionPolicy(
    val allowMultipleRedemptions: Boolean,
    val allowOfflineRedemption: Boolean,
    val maxRedemptionsPerUser: Int
)
