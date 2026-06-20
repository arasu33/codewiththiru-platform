package com.codewiththiru.ads.interstitial

import com.codewiththiru.ads.api.AdsEnvironment
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.config.AdsConfig
import com.codewiththiru.ads.storage.AdsStorageProvider

/**
 * Result of evaluating the Interstitial display policy.
 */
sealed class PolicyResult {
    object Allowed : PolicyResult()
    data class Denied(val reason: String) : PolicyResult()
}

/**
 * Engine to determine if an Interstitial ad is permitted to be requested or shown.
 */
class InterstitialPolicy(
    private val config: AdsConfig,
    private val storage: AdsStorageProvider,
    private val environment: AdsEnvironment
) {

    /**
     * Checks whether an interstitial ad is allowed to be loaded or shown right now.
     */
    suspend fun evaluate(isCriticalFlow: Boolean): PolicyResult {
        if (!config.adsEnabled) {
            return PolicyResult.Denied("Ads globally disabled")
        }

        if (isCriticalFlow) {
            return PolicyResult.Denied("Critical flow active")
        }

        val lastImpressionTime = storage.getLastImpressionTime(AdType.Interstitial)
        val timeSinceLastAd = System.currentTimeMillis() - lastImpressionTime

        // Assume there's a cooldown setting in AdsConfig. We'll default to 30s if not specified.
        // For simplicity we will assume 30,000 ms.
        val cooldownMs = 30_000L 
        if (timeSinceLastAd < cooldownMs) {
            return PolicyResult.Denied("Cooldown active")
        }

        // Add additional session limits checks here
        
        return PolicyResult.Allowed
    }
}
