package com.codewiththiru.platform.coupons.security

interface CouponFraudDetector {
    suspend fun isSuspiciousAttempt(code: String): Boolean

    suspend fun recordAttempt(
        code: String,
        success: Boolean,
    )
}
