package com.codewiththiru.platform.coupons.domain.model

data class CouponTriggerContext(
    val appPackage: String,
    val feature: String,
    val source: CouponSource
)
