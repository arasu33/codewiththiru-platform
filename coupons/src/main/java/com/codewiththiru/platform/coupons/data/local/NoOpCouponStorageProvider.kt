package com.codewiththiru.platform.coupons.data.local

class NoOpCouponStorageProvider : CouponStorageProvider {
    override suspend fun save(key: String, data: String) {
        // No-op
    }

    override suspend fun read(key: String): String? {
        return null
    }

    override suspend fun clear() {
        // No-op
    }
}
