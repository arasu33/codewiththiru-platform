package com.codewiththiru.platform.coupons.provider

import com.codewiththiru.platform.coupons.domain.model.CouponReward

interface CouponFeatureGate {
    suspend fun unlock(reward: CouponReward)
}
