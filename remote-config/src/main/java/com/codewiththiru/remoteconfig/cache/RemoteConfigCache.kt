package com.codewiththiru.remoteconfig.cache

interface RemoteConfigCache {
    suspend fun save(key: String, value: String)
    suspend fun save(key: String, value: Boolean)
    suspend fun save(key: String, value: Int)
    suspend fun save(key: String, value: Long)
    suspend fun save(key: String, value: Double)
    
    suspend fun getString(key: String): String?
    suspend fun getBoolean(key: String): Boolean?
    suspend fun getInt(key: String): Int?
    suspend fun getLong(key: String): Long?
    suspend fun getDouble(key: String): Double?
    
    suspend fun clear()
}
