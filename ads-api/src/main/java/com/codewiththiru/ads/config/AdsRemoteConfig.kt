package com.codewiththiru.ads.config

import com.codewiththiru.ads.api.AdType

/**
 * Interface representing the remote configuration source for Ads.
 */
interface AdsRemoteConfig {
    /**
     * @return True if ads are enabled globally from the remote config, false otherwise.
     * Null means no remote override is set (fallback to local config).
     */
    fun isAdsGlobalEnabled(): Boolean?

    /**
     * @return True if the specific [adType] is enabled, false if it's disabled.
     * Null means no remote override is set.
     */
    fun isAdFormatEnabled(adType: AdType): Boolean?

    /**
     * @return The overridden Ad Unit ID for the given [adType] and [network].
     * Null if no override exists.
     */
    fun getAdUnitIdOverride(
        adType: AdType,
        network: String,
    ): String?
}
