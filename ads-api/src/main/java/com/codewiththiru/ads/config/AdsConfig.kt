package com.codewiththiru.ads.config

import com.codewiththiru.ads.api.AdType
import kotlinx.serialization.Serializable

/**
 * Configuration for the Ads SDK, generally populated from Remote Config.
 */
@Serializable
data class AdsConfig(
    val adsEnabled: Boolean = true,
    val bannerEnabled: Boolean = true,
    val interstitialEnabled: Boolean = true,
    val rewardedEnabled: Boolean = true,
    val rewardedInterstitialEnabled: Boolean = true,
    val nativeEnabled: Boolean = true,
    val appOpenEnabled: Boolean = true,
    val testMode: Boolean = false,
    val frequencyCapEnabled: Boolean = true,
    val revenueTrackingEnabled: Boolean = true,
    val consentRequired: Boolean = true,
    val adRefreshIntervalSeconds: Int = 30,
    val preloadCounts: Map<String, Int> = emptyMap(),
    val killSwitch: Boolean = false,
) {
    /**
     * Resolves whether ads are enabled globally, respecting the remote kill switch override.
     */
    fun isGlobalEnabled(remoteConfig: AdsRemoteConfig?): Boolean {
        if (killSwitch) return false
        val remoteEnabled = remoteConfig?.isAdsGlobalEnabled()
        return remoteEnabled ?: adsEnabled
    }

    /**
     * Resolves whether a specific ad format is enabled.
     */
    fun isFormatEnabled(
        adType: AdType,
        remoteConfig: AdsRemoteConfig?,
    ): Boolean {
        if (!isGlobalEnabled(remoteConfig)) {
            return false
        }

        val remoteFormatEnabled = remoteConfig?.isAdFormatEnabled(adType)
        return remoteFormatEnabled ?: when (adType) {
            AdType.Banner, AdType.AdaptiveBanner -> bannerEnabled
            AdType.Interstitial -> interstitialEnabled
            AdType.Rewarded -> rewardedEnabled
            AdType.RewardedInterstitial -> rewardedInterstitialEnabled
            AdType.Native -> nativeEnabled
            AdType.AppOpen -> appOpenEnabled
        }
    }
}
