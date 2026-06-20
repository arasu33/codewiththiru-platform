package com.codewiththiru.platform.coupons.domain.model

data class CouponCampaign(
    val id: String,
    val name: String,
    val startsAt: Long,
    val endsAt: Long,
    val active: Boolean
)
