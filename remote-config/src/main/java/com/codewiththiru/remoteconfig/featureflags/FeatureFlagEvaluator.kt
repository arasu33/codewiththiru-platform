package com.codewiththiru.remoteconfig.featureflags

import android.util.Log
import com.codewiththiru.remoteconfig.api.RemoteConfigKey
import com.codewiththiru.remoteconfig.api.RemoteConfigManager

class FeatureFlagEvaluator(
    private val configManager: RemoteConfigManager,
    private val registry: FeatureFlagRegistry
) {
    suspend fun isFeatureEnabled(key: String, defaultValue: Boolean = false): Boolean {
        val metadata = registry.getMetadata(key)
        if (metadata != null) {
            when (metadata.lifecycle.status) {
                FeatureFlagStatus.ARCHIVED -> {
                    Log.w("FeatureFlag", "Evaluating archived flag: $key. Returning default.")
                    return defaultValue
                }
                FeatureFlagStatus.DEPRECATED -> {
                    Log.w("FeatureFlag", "Evaluating deprecated flag: $key. Consider removing.")
                }
                FeatureFlagStatus.DRAFT -> {
                    // Draft flags could have special routing or be ignored by default
                }
                FeatureFlagStatus.ACTIVE -> {
                    // Normal execution
                }
            }
        }
        
        return configManager.getBoolean(key, defaultValue)
    }
}
