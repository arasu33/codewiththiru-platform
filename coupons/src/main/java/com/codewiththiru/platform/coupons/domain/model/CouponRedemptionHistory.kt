package com.codewiththiru.platform.coupons.domain.model

data class CouponRedemptionHistory(
    val couponCode: String,
    val redeemedAt: Long,
    val campaignId: String,
    val rewardGranted: CouponReward
)
