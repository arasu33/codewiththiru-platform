package com.codewiththiru.platform.coupons.data.repository

import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.model.CouponResult
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import com.codewiththiru.platform.coupons.domain.model.CouponTriggerContext

interface CouponRepository {
    suspend fun validateCoupon(code: String, context: CouponTriggerContext): CouponResult<CouponModel>
    suspend fun redeemCoupon(code: String): CouponResult<CouponReward>
    suspend fun getCachedCoupon(code: String): CouponModel?
    suspend fun saveCoupon(coupon: CouponModel)
}
