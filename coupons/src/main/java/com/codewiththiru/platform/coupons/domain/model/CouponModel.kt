package com.codewiththiru.platform.coupons.domain.model

data class CouponModel(
    val code: String,
    val campaign: CouponCampaign,
    val expiresAt: Long,
    val metadata: Map<String, String> = emptyMap(),
)
