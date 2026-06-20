package com.codewiththiru.ads.config

import com.codewiththiru.ads.api.AdType

/**
 * Concrete implementation mapping Remote Config properties to specific Ad toggles.
 * In a full production setup, this class wraps FirebaseRemoteConfig or another config SDK.
 */
class PlatformAdsRemoteConfig(
    // private val remoteConfigProvider: RemoteConfigProvider
) : AdsRemoteConfig {

    override fun isAdsGlobalEnabled(): Boolean? {
        // return remoteConfigProvider.getBoolean("ads_global_enabled")
        return null // Fallback to local config
    }

    override fun isAdFormatEnabled(adType: AdType): Boolean? {
        // val key = "ads_format_enabled_${adType.name.lowercase()}"
        // return remoteConfigProvider.getBoolean(key)
        return null // Fallback to local config
    }

    override fun getAdUnitIdOverride(adType: AdType, network: String): String? {
        // val key = "ads_unit_id_${adType.name.lowercase()}_${network.lowercase()}"
        // return remoteConfigProvider.getString(key).takeIf { it.isNotBlank() }
        return null // Fallback to local config
    }
}
