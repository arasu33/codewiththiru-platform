package com.codewiththiru.ads.interstitial

import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.repository.AdsRepository
import com.codewiththiru.ads.state.AdState
import kotlinx.coroutines.flow.StateFlow

/**
 * Enterprise manager specifically designed to handle Interstitial Ads safely.
 */
class InterstitialManager(
    private val policy: InterstitialPolicy,
    private val repository: AdsRepository,
    private val analytics: InterstitialAnalytics
) {

    /**
     * Attempts to show an Interstitial Ad.
     * Evaluates policy rules before allowing the display to prevent disrupting critical user flows.
     */
    suspend fun show(isCriticalFlow: Boolean = false, callback: InterstitialCallback? = null) {
        val policyResult = policy.evaluate(isCriticalFlow)
        
        when (policyResult) {
            is PolicyResult.Allowed -> {
                // If the ad state is loaded, we can show it
                if (repository.observeState(AdType.Interstitial).value is AdState.Loaded) {
                    repository.show(AdType.Interstitial)
                } else {
                    analytics.onFailedToLoad(0, "Ad not loaded when show requested")
                    callback?.onAdFailedToShow(Exception("Ad not ready"))
                    // Trigger a load for next time
                    repository.load(AdType.Interstitial)
                }
            }
            is PolicyResult.Denied -> {
                analytics.onBlockedByPolicy(policyResult.reason)
                callback?.onAdFailedToShow(Exception("Blocked by policy: ${policyResult.reason}"))
            }
        }
    }

    /**
     * Preloads an interstitial ad into memory if the policy allows.
     */
    suspend fun load() {
        // Only load if globally allowed
        if (policy.evaluate(false) is PolicyResult.Allowed) {
            repository.load(AdType.Interstitial)
            analytics.onRequested()
        } else {
            analytics.onBlockedByPolicy("Preload blocked by policy")
        }
    }

    /**
     * Exposes the reactive state of the Interstitial ad for UI layers.
     */
    fun observeState(): StateFlow<AdState> {
        return repository.observeState(AdType.Interstitial)
    }
}
