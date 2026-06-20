package com.codewiththiru.platform.coupons.data.local

class EncryptedCouponStorageProvider : CouponStorageProvider {
    override suspend fun save(key: String, data: String) {
        // TODO: Implement encrypted storage using EncryptedSharedPreferences or DataStore
    }

    override suspend fun read(key: String): String? {
        // TODO: Implement encrypted read
        return null
    }

    override suspend fun clear() {
        // TODO: Implement clear
    }
}
