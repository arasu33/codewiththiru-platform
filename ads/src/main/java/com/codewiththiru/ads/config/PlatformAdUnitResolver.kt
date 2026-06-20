package com.codewiththiru.ads.config

import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.api.AdUnitResolver
import com.codewiththiru.ads.api.AdsEnvironment

/**
 * Concrete implementation that resolves Ad Unit IDs from Remote Config, 
 * local defaults, and enforces strict security around Debug vs Production IDs.
 */
class PlatformAdUnitResolver(
    private val remoteConfig: AdsRemoteConfig,
    private val isDebug: Boolean // Passed from BuildConfig.DEBUG of the app
) : AdUnitResolver {

    override fun resolve(
        environment: AdsEnvironment,
        config: AdsConfig,
        adType: AdType
    ): String {
        // 1. Check for remote config override
        val remoteOverride = remoteConfig.getAdUnitIdOverride(adType, "admob")
        if (!remoteOverride.isNullOrBlank()) {
            return validateAndReturn(remoteOverride, environment)
        }

        // 2. Fallback to standard logic based on environment
        val defaultId = if (environment.isTestEnvironment) {
            getTestAdUnitId(adType)
        } else {
            getProductionAdUnitId(adType)
        }

        return validateAndReturn(defaultId, environment)
    }

    /**
     * Enforces security policy: Production IDs cannot be used in Debug/Test environments.
     */
    private fun validateAndReturn(adUnitId: String, environment: AdsEnvironment): String {
        val isTestEnvironment = environment.isTestEnvironment || isDebug
        val isTestId = adUnitId.contains("3940256099942544") // AdMob standard test ID prefix

        if (isTestEnvironment && !isTestId && adUnitId.isNotBlank()) {
            // Leak prevention: If we are in debug/test but using a real ID, we throw an error in debug
            // or fallback to test ID if we want to be safe.
            throw IllegalStateException("SECURITY VIOLATION: Production Ad Unit ID ($adUnitId) used in Debug/Test environment!")
        }

        if (environment == AdsEnvironment.Production && isTestId) {
            // We should never use test IDs in production
            throw IllegalStateException("SECURITY VIOLATION: Test Ad Unit ID ($adUnitId) used in Production environment!")
        }

        return adUnitId
    }

    private fun getTestAdUnitId(adType: AdType): String {
        // Official AdMob Test IDs
        return when (adType) {
            AdType.Banner, AdType.AdaptiveBanner -> "ca-app-pub-3940256099942544/6300978111"
            AdType.Interstitial -> "ca-app-pub-3940256099942544/1033173712"
            AdType.Rewarded -> "ca-app-pub-3940256099942544/5224354917"
            AdType.RewardedInterstitial -> "ca-app-pub-3940256099942544/5354046379"
            AdType.Native -> "ca-app-pub-3940256099942544/2247696110"
            AdType.AppOpen -> "ca-app-pub-3940256099942544/3419835294"
        }
    }

    private fun getProductionAdUnitId(adType: AdType): String {
        // In a real app, these come from strings.xml, local config, or DI.
        // For security, never hardcode actual production IDs in open repositories.
        return "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"
    }
}
