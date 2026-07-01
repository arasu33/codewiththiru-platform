package com.codewiththiru.remoteconfig.api

import com.codewiththiru.remoteconfig.analytics.RemoteConfigAnalyticsProvider
import com.codewiththiru.remoteconfig.analytics.RemoteConfigEvent
import com.codewiththiru.remoteconfig.cache.RemoteConfigCache
import com.codewiththiru.remoteconfig.provider.RemoteConfigProvider
import com.codewiththiru.remoteconfig.security.KillSwitchManager
import com.codewiththiru.remoteconfig.security.RemoteConfigSecurityValidator
import com.codewiththiru.remoteconfig.state.RemoteConfigState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DefaultRemoteConfigManager(
    private val memoryCache: RemoteConfigCache,
    private val dataStoreCache: RemoteConfigCache,
    private val compositeProvider: RemoteConfigProvider,
    private val securityValidator: RemoteConfigSecurityValidator,
    private val killSwitchManager: KillSwitchManager,
    private val analyticsProvider: RemoteConfigAnalyticsProvider
) : RemoteConfigManager {

    private val _state = MutableStateFlow<RemoteConfigState>(RemoteConfigState.Idle)
    override val state: StateFlow<RemoteConfigState> = _state.asStateFlow()

    private val localOverrides = java.util.concurrent.ConcurrentHashMap<String, Any>()

    private val refreshMutex = Mutex()
    private var lastFetchTime = 0L
    private val REFRESH_INTERVAL_MS = 6 * 60 * 60 * 1000L // 6 Hours

    override suspend fun initialize() {
        compositeProvider.initialize()
        
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastFetchTime > REFRESH_INTERVAL_MS) {
            refresh()
        }
    }

    override suspend fun refresh() {
        refreshMutex.withLock {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastFetchTime > REFRESH_INTERVAL_MS) {
                performFetch()
            }
        }
    }

    override suspend fun forceRefresh() {
        refreshMutex.withLock {
            performFetch()
        }
    }

    override suspend fun clearCache() {
        memoryCache.clear()
        dataStoreCache.clear()
    }

    override suspend fun <T> setOverride(key: com.codewiththiru.remoteconfig.api.RemoteConfigKey<T>, value: T) {
        localOverrides[key.key] = value as Any
    }

    override suspend fun clearOverrides() {
        localOverrides.clear()
    }

    private suspend fun performFetch() {
        _state.value = RemoteConfigState.Loading
        analyticsProvider.trackEvent(RemoteConfigEvent.REMOTE_CONFIG_FETCH_STARTED)

        try {
            val result = compositeProvider.fetchAndActivate()
            if (result.isSuccess) {
                lastFetchTime = System.currentTimeMillis()
                analyticsProvider.trackEvent(RemoteConfigEvent.REMOTE_CONFIG_FETCH_SUCCESS)
                
                memoryCache.clear()
                _state.value = RemoteConfigState.Success
            } else {
                handleFetchFailure(result.exceptionOrNull())
            }
        } catch (e: Exception) {
            handleFetchFailure(e)
        }
    }

    private fun handleFetchFailure(exception: Throwable?) {
        analyticsProvider.trackEvent(
            RemoteConfigEvent.REMOTE_CONFIG_FETCH_FAILED,
            mapOf("error" to (exception?.message ?: "Unknown Error"))
        )
        _state.value = RemoteConfigState.Error(exception ?: Exception("Unknown Fetch Error"))
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> getValue(key: RemoteConfigKey<T>): T {
        if (killSwitchManager.isKillSwitchActive(key.key)) {
            analyticsProvider.trackEvent(RemoteConfigEvent.KILL_SWITCH_TRIGGERED, mapOf("key" to key.key))
            return key.defaultValue
        }

        if (localOverrides.containsKey(key.key)) {
            // Suppress standard telemetry for overrides to avoid polluting production data
            android.util.Log.d("CWT_PLATFORM", "Using local override for RemoteConfig key: ${key.key}")
            return localOverrides[key.key] as T
        }

        val memValue = readFromCache(memoryCache, key)
        if (memValue != null) {
            analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_HIT, mapOf("key" to key.key, "layer" to "Memory"))
            return memValue
        }

        val dsValue = readFromCache(dataStoreCache, key)
        if (dsValue != null) {
            analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_HIT, mapOf("key" to key.key, "layer" to "DataStore"))
            saveToCache(memoryCache, key, dsValue)
            return dsValue
        }
        
        analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_MISS, mapOf("key" to key.key))

        val providerValue = readFromProvider(compositeProvider, key)
        if (providerValue != null) {
            saveToCache(memoryCache, key, providerValue)
            saveToCache(dataStoreCache, key, providerValue)
            return providerValue
        }

        return key.defaultValue
    }

    override fun getString(key: String, defaultValue: String): String {
        return compositeProvider.getString(key) ?: defaultValue
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return compositeProvider.getBoolean(key) ?: defaultValue
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return compositeProvider.getInt(key) ?: defaultValue
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return compositeProvider.getLong(key) ?: defaultValue
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return compositeProvider.getDouble(key) ?: defaultValue
    }

    override fun getJson(key: String, defaultValue: String): String {
        return compositeProvider.getString(key) ?: defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> readFromCache(cache: RemoteConfigCache, key: RemoteConfigKey<T>): T? {
        return when (key.defaultValue) {
            is String -> cache.getString(key.key) as T?
            is Boolean -> cache.getBoolean(key.key) as T?
            is Int -> cache.getInt(key.key) as T?
            is Long -> cache.getLong(key.key) as T?
            is Double -> cache.getDouble(key.key) as T?
            else -> null
        }
    }

    private suspend fun <T> saveToCache(cache: RemoteConfigCache, key: RemoteConfigKey<T>, value: T) {
        when (value) {
            is String -> cache.save(key.key, value)
            is Boolean -> cache.save(key.key, value)
            is Int -> cache.save(key.key, value)
            is Long -> cache.save(key.key, value)
            is Double -> cache.save(key.key, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> readFromProvider(provider: RemoteConfigProvider, key: RemoteConfigKey<T>): T? {
        return when (key.defaultValue) {
            is String -> provider.getString(key.key) as T?
            is Boolean -> provider.getBoolean(key.key) as T?
            is Int -> provider.getInt(key.key) as T?
            is Long -> provider.getLong(key.key) as T?
            is Double -> provider.getDouble(key.key) as T?
            else -> null
        }
    }
}
