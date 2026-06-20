package com.codewiththiru.platform.coupons.domain.validator.rules

import com.codewiththiru.platform.coupons.domain.validator.CouponValidator

class FormatValidationRule : CouponValidator {
    override suspend fun isValid(code: String): Boolean {
        // Basic example: Alphanumeric, 5-20 characters
        if (code.isBlank() || code.length !in 5..20) {
            return false
        }
        val regex = Regex("^[a-zA-Z0-9_-]+$")
        return regex.matches(code)
    }
}
