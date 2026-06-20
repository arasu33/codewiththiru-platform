package com.codewiththiru.platform.coupons.domain.validator.rules

import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.validator.CouponModelValidator
import com.codewiththiru.platform.coupons.provider.CouponClock

class ExpiryValidationRule(
    private val clock: CouponClock
) : CouponModelValidator {
    override suspend fun validate(model: CouponModel): CouponErrorCode? {
        val now = clock.currentTimeMillis()
        if (model.expiresAt in 1..<now) {
            return CouponErrorCode.Expired
        }
        return null
    }
}
