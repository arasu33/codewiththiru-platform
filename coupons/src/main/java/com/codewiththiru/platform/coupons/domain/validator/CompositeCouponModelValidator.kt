package com.codewiththiru.platform.coupons.domain.validator

import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel

class CompositeCouponModelValidator(
    private val rules: List<CouponModelValidator>
) : CouponModelValidator {
    override suspend fun validate(model: CouponModel): CouponErrorCode? {
        for (rule in rules) {
            val error = rule.validate(model)
            if (error != null) {
                return error
            }
        }
        return null
    }
}
