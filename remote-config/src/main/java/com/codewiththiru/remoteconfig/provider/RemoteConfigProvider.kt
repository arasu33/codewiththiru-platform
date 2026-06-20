package com.codewiththiru.remoteconfig.provider

import com.codewiththiru.remoteconfig.state.RemoteConfigState

interface RemoteConfigProvider {
    val name: String
    suspend fun initialize()
    suspend fun fetch(): Result<Unit>
    suspend fun activate(): Boolean
    suspend fun fetchAndActivate(): Result<Boolean>
    
    fun getString(key: String): String?
    fun getBoolean(key: String): Boolean?
    fun getInt(key: String): Int?
    fun getLong(key: String): Long?
    fun getDouble(key: String): Double?
}
