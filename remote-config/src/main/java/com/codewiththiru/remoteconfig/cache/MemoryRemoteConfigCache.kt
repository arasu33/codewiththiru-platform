package com.codewiththiru.remoteconfig.cache

import java.util.concurrent.ConcurrentHashMap

class MemoryRemoteConfigCache : RemoteConfigCache {
    
    private val stringCache = ConcurrentHashMap<String, String>()
    private val booleanCache = ConcurrentHashMap<String, Boolean>()
    private val intCache = ConcurrentHashMap<String, Int>()
    private val longCache = ConcurrentHashMap<String, Long>()
    private val doubleCache = ConcurrentHashMap<String, Double>()

    override suspend fun save(key: String, value: String) {
        stringCache[key] = value
    }

    override suspend fun save(key: String, value: Boolean) {
        booleanCache[key] = value
    }

    override suspend fun save(key: String, value: Int) {
        intCache[key] = value
    }

    override suspend fun save(key: String, value: Long) {
        longCache[key] = value
    }

    override suspend fun save(key: String, value: Double) {
        doubleCache[key] = value
    }

    override suspend fun getString(key: String): String? = stringCache[key]
    override suspend fun getBoolean(key: String): Boolean? = booleanCache[key]
    override suspend fun getInt(key: String): Int? = intCache[key]
    override suspend fun getLong(key: String): Long? = longCache[key]
    override suspend fun getDouble(key: String): Double? = doubleCache[key]

    override suspend fun clear() {
        stringCache.clear()
        booleanCache.clear()
        intCache.clear()
        longCache.clear()
        doubleCache.clear()
    }
}
