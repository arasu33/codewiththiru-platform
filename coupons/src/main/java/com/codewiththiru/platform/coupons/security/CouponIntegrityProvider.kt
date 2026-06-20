package com.codewiththiru.platform.coupons.security

interface CouponIntegrityProvider {
    suspend fun verifyIntegrity(): Boolean
}
