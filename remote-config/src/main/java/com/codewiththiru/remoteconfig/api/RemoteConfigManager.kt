package com.codewiththiru.remoteconfig.api

import com.codewiththiru.remoteconfig.state.RemoteConfigState
import kotlinx.coroutines.flow.StateFlow

interface RemoteConfigManager {
    val state: StateFlow<RemoteConfigState>
    
    suspend fun initialize()
    suspend fun refresh()
    suspend fun forceRefresh()
    suspend fun clearCache()

    fun getString(key: String, defaultValue: String = ""): String
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun getLong(key: String, defaultValue: Long = 0L): Long
    fun getDouble(key: String, defaultValue: Double = 0.0): Double
    fun getJson(key: String, defaultValue: String = "{}"): String

    suspend fun <T> getValue(key: RemoteConfigKey<T>): T
}
