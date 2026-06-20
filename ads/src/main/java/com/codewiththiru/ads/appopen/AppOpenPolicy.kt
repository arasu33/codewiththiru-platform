package com.codewiththiru.ads.appopen

import com.codewiththiru.ads.config.AdsConfig
import com.codewiththiru.ads.storage.AdsStorageProvider

/**
 * Result of evaluating the App Open Ad display policy.
 */
sealed class AppOpenPolicyResult {
    object Allowed : AppOpenPolicyResult()
    data class Denied(val reason: String) : AppOpenPolicyResult()
}

/**
 * Policy ruleset for determining whether an App Open ad should be requested or shown.
 */
class AppOpenPolicy(
    private val config: AdsConfig,
    private val storage: AdsStorageProvider
) {
    /**
     * Maximum time an ad can be cached before it is considered expired (4 hours in milliseconds).
     */
    private val EXPIRATION_DURATION_MS = 4L * 3600L * 1000L

    /**
     * Determines if a newly loaded or cached ad is still valid.
     */
    fun isAdAvailable(loadTimeMs: Long): Boolean {
        if (loadTimeMs <= 0) return false
        val timeSinceLoad = System.currentTimeMillis() - loadTimeMs
        return timeSinceLoad < EXPIRATION_DURATION_MS
    }

    /**
     * Evaluates if we are permitted to show the App Open ad right now.
     */
    fun evaluate(isShowingAnotherAd: Boolean): AppOpenPolicyResult {
        if (!config.adsEnabled) {
            return AppOpenPolicyResult.Denied("Ads globally disabled")
        }

        if (isShowingAnotherAd) {
            return AppOpenPolicyResult.Denied("Another ad is already showing")
        }

        // Add additional frequency or cooldown checks if needed

        return AppOpenPolicyResult.Allowed
    }
}
