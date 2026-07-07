package com.codewiththiru.platform.coupons.data.local

interface CouponStorageProvider {
    suspend fun save(
        key: String,
        data: String,
    )

    suspend fun read(key: String): String?

    suspend fun clear()
}
