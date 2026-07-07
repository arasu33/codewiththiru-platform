package com.codewiththiru.platform.coupons.data.local

import java.util.concurrent.ConcurrentHashMap

class MemoryCouponStorageProvider : CouponStorageProvider {
    private val storage = ConcurrentHashMap<String, String>()

    override suspend fun save(
        key: String,
        data: String,
    ) {
        storage[key] = data
    }

    override suspend fun read(key: String): String? = storage[key]

    override suspend fun clear() {
        storage.clear()
    }
}
