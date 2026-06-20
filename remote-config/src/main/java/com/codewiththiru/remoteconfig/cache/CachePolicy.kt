package com.codewiththiru.remoteconfig.cache

data class CachePolicy(
    val memoryCacheEnabled: Boolean = true,
    val persistentCacheEnabled: Boolean = true,
    val maxAgeMs: Long = 6 * 60 * 60 * 1000L // 6 hours
)
