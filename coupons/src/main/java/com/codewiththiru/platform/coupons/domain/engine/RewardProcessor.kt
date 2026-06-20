package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.domain.model.CouponResult
import com.codewiththiru.platform.coupons.domain.model.CouponReward

interface RewardProcessor {
    suspend fun processReward(reward: CouponReward): CouponResult<Unit>
}
