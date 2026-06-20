package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.model.CouponRedemptionHistory
import com.codewiththiru.platform.coupons.domain.model.CouponReward

interface RedemptionHistoryTracker {
    suspend fun recordRedemption(model: CouponModel, reward: CouponReward)
    suspend fun getHistory(couponCode: String): List<CouponRedemptionHistory>
    suspend fun hasBeenRedeemed(couponCode: String): Boolean
}
