package com.codewiththiru.remoteconfig.provider.fallback

import com.codewiththiru.remoteconfig.cache.CacheMigrationManager
import com.codewiththiru.remoteconfig.cache.CachedRemoteConfig
import android.util.Log

class RemoteConfigRecoveryManager(
    private val emergencyFallbackConfig: EmergencyFallbackConfig,
    private val migrationManager: CacheMigrationManager
) {
    var currentSafeModeConfig = SafeModeConfig()
        private set

    private var lastKnownGoodConfig: CachedRemoteConfig? = null

    fun activateSafeMode(config: SafeModeConfig) {
        Log.e("RecoveryManager", "SAFE MODE ACTIVATED: $config")
        currentSafeModeConfig = config
    }

    fun deactivateSafeMode() {
        Log.i("RecoveryManager", "SAFE MODE DEACTIVATED")
        currentSafeModeConfig = SafeModeConfig()
    }

    fun saveLastKnownGoodConfig(config: CachedRemoteConfig) {
        lastKnownGoodConfig = config
    }

    fun restoreLastKnownGoodConfig(): CachedRemoteConfig? {
        if (lastKnownGoodConfig != null) {
            Log.w("RecoveryManager", "Restoring last known good config")
            return lastKnownGoodConfig
        }
        return null
    }

    fun getEmergencyValue(key: String): Any? {
        return emergencyFallbackConfig.getFallbackValue(key)
    }
}
