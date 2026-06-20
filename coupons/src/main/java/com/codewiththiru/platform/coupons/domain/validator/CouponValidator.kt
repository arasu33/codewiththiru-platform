package com.codewiththiru.platform.coupons.domain.validator

import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel

interface CouponValidator {
    suspend fun isValid(code: String): Boolean
}

interface CouponModelValidator {
    suspend fun validate(model: CouponModel): CouponErrorCode?
}
