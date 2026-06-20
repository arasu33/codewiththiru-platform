package com.codewiththiru.platform.coupons.config

data class CouponCooldownPolicy(
    val maxAttempts: Int,
    val cooldownMinutes: Int
)
