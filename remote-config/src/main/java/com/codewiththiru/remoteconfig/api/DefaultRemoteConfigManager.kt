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
import kotlinx.coroutines.launch

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
    private val inMemoryValues = java.util.concurrent.ConcurrentHashMap<String, Any>()

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
        inMemoryValues.clear()
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
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
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
        return getInternalValue(key.key, key.defaultValue)
    }

    override fun getString(key: String, defaultValue: String): String = getSynchronousInternalValue(key, defaultValue)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = getSynchronousInternalValue(key, defaultValue)

    override fun getInt(key: String, defaultValue: Int): Int = getSynchronousInternalValue(key, defaultValue)

    override fun getLong(key: String, defaultValue: Long): Long = getSynchronousInternalValue(key, defaultValue)

    override fun getDouble(key: String, defaultValue: Double): Double = getSynchronousInternalValue(key, defaultValue)

    override fun getJson(key: String, defaultValue: String): String = getSynchronousInternalValue(key, defaultValue)

    @Suppress("UNCHECKED_CAST")
    private fun <T> getSynchronousInternalValue(keyName: String, defaultValue: T): T {
        if (killSwitchManager.isKillSwitchActive(keyName)) {
            analyticsProvider.trackEvent(RemoteConfigEvent.KILL_SWITCH_TRIGGERED, mapOf("key" to keyName))
            return defaultValue
        }

        if (localOverrides.containsKey(keyName)) {
            android.util.Log.d("CWT_PLATFORM", "Using local override for RemoteConfig key: $keyName")
            return localOverrides[keyName] as T
        }

        val cached = inMemoryValues[keyName]
        if (cached != null) {
            analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_HIT, mapOf("key" to keyName, "layer" to "FastMemory"))
            return cached as T
        }

        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            try {
                getInternalValue(keyName, defaultValue)
            } catch (e: Exception) {
                // Ignore
            }
        }

        return defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> getInternalValue(keyName: String, defaultValue: T): T {
        if (killSwitchManager.isKillSwitchActive(keyName)) {
            analyticsProvider.trackEvent(RemoteConfigEvent.KILL_SWITCH_TRIGGERED, mapOf("key" to keyName))
            return defaultValue
        }

        if (localOverrides.containsKey(keyName)) {
            // Suppress standard telemetry for overrides to avoid polluting production data
            android.util.Log.d("CWT_PLATFORM", "Using local override for RemoteConfig key: $keyName")
            return localOverrides[keyName] as T
        }

        val memValue = readFromCacheInternal(memoryCache, keyName, defaultValue)
        if (memValue != null) {
            inMemoryValues[keyName] = memValue as Any
            analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_HIT, mapOf("key" to keyName, "layer" to "Memory"))
            return memValue
        }

        val dsValue = readFromCacheInternal(dataStoreCache, keyName, defaultValue)
        if (dsValue != null) {
            inMemoryValues[keyName] = dsValue as Any
            analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_HIT, mapOf("key" to keyName, "layer" to "DataStore"))
            saveToCacheInternal(memoryCache, keyName, dsValue)
            return dsValue
        }
        
        analyticsProvider.trackEvent(RemoteConfigEvent.CACHE_MISS, mapOf("key" to keyName))

        val providerValue = readFromProviderInternal(compositeProvider, keyName, defaultValue)
        if (providerValue != null) {
            inMemoryValues[keyName] = providerValue as Any
            saveToCacheInternal(memoryCache, keyName, providerValue)
            saveToCacheInternal(dataStoreCache, keyName, providerValue)
            return providerValue
        }

        return defaultValue
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> readFromCacheInternal(cache: RemoteConfigCache, keyName: String, defaultValue: T): T? {
        return when (defaultValue) {
            is String -> cache.getString(keyName) as T?
            is Boolean -> cache.getBoolean(keyName) as T?
            is Int -> cache.getInt(keyName) as T?
            is Long -> cache.getLong(keyName) as T?
            is Double -> cache.getDouble(keyName) as T?
            else -> null
        }
    }

    private suspend fun <T> saveToCacheInternal(cache: RemoteConfigCache, keyName: String, value: T) {
        when (value) {
            is String -> cache.save(keyName, value)
            is Boolean -> cache.save(keyName, value)
            is Int -> cache.save(keyName, value)
            is Long -> cache.save(keyName, value)
            is Double -> cache.save(keyName, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> readFromProviderInternal(provider: RemoteConfigProvider, keyName: String, defaultValue: T): T? {
        return when (defaultValue) {
            is String -> provider.getString(keyName) as T?
            is Boolean -> provider.getBoolean(keyName) as T?
            is Int -> provider.getInt(keyName) as T?
            is Long -> provider.getLong(keyName) as T?
            is Double -> provider.getDouble(keyName) as T?
            else -> null
        }
    }
}
