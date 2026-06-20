package com.codewiththiru.platform.coupons.provider

interface CouponCleanupProvider {
    suspend fun cleanupExpired()
}
