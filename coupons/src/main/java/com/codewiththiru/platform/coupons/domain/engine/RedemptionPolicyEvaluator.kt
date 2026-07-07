package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.config.CouponRedemptionPolicy
import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel

interface RedemptionPolicyEvaluator {
    suspend fun evaluate(
        model: CouponModel,
        policy: CouponRedemptionPolicy,
    ): CouponErrorCode?
}
