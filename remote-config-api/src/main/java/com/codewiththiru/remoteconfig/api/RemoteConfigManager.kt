package com.codewiththiru.remoteconfig.api

import com.codewiththiru.remoteconfig.state.RemoteConfigState
import kotlinx.coroutines.flow.StateFlow

/**
 * Core interface for managing and accessing remote configurations.
 *
 * Provides methods to fetch, cache, and retrieve configuration values.
 * Implementations should handle fallback mechanisms and caching strategies automatically.
 */
interface RemoteConfigManager {
    /**
     * A flow emitting the current initialization and synchronization state of the config manager.
     */
    val state: StateFlow<RemoteConfigState>

    /**
     * Initializes the manager, loading cached configurations from disk.
     * Should be called during application startup.
     */
    suspend fun initialize()

    /**
     * Fetches the latest configuration values from the remote server if the cache has expired.
     */
    suspend fun refresh()

    /**
     * Forces a refresh of the configuration values from the remote server, bypassing any cache expiration checks.
     */
    suspend fun forceRefresh()

    /**
     * Clears all cached configurations. Useful when a user logs out or requests a data wipe.
     */
    suspend fun clearCache()

    /**
     * Retrieves a string value for the given [key].
     * Returns [defaultValue] if the key is not found or fails to parse.
     */
    fun getString(
        key: String,
        defaultValue: String = "",
    ): String

    /**
     * Retrieves a boolean value for the given [key].
     * Returns [defaultValue] if the key is not found or fails to parse.
     */
    fun getBoolean(
        key: String,
        defaultValue: Boolean = false,
    ): Boolean

    /**
     * Retrieves an integer value for the given [key].
     * Returns [defaultValue] if the key is not found or fails to parse.
     */
    fun getInt(
        key: String,
        defaultValue: Int = 0,
    ): Int

    /**
     * Retrieves a long value for the given [key].
     * Returns [defaultValue] if the key is not found or fails to parse.
     */
    fun getLong(
        key: String,
        defaultValue: Long = 0L,
    ): Long

    /**
     * Retrieves a double value for the given [key].
     * Returns [defaultValue] if the key is not found or fails to parse.
     */
    fun getDouble(
        key: String,
        defaultValue: Double = 0.0,
    ): Double

    /**
     * Retrieves a JSON string value for the given [key].
     * Returns [defaultValue] if the key is not found.
     */
    fun getJson(
        key: String,
        defaultValue: String = "{}",
    ): String

    /**
     * Retrieves a value type-safely based on the provided [RemoteConfigKey].
     */
    suspend fun <T> getValue(key: RemoteConfigKey<T>): T

    /**
     * Sets a local override for the specified [key].
     * Useful for debugging or developer menus.
     */
    suspend fun <T> setOverride(
        key: RemoteConfigKey<T>,
        value: T,
    )

    /**
     * Clears all local overrides previously set by [setOverride].
     */
    suspend fun clearOverrides()
}
