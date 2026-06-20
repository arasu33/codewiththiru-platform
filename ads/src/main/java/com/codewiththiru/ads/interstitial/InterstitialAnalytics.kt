package com.codewiththiru.ads.interstitial

import com.codewiththiru.ads.analytics.AdsAnalyticsProvider
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.revenue.AdRevenueTracker

/**
 * Handles analytics and revenue tracking specific to Interstitial Ads.
 */
class InterstitialAnalytics(
    private val analyticsProvider: AdsAnalyticsProvider,
    private val revenueTracker: AdRevenueTracker
) {

    fun onRequested() {
        analyticsProvider.onAdRequested(AdType.Interstitial)
    }

    fun onLoaded(loadTimeMs: Long) {
        analyticsProvider.onAdLoaded(AdType.Interstitial, loadTimeMs)
    }

    fun onFailedToLoad(errorCode: Int, errorMessage: String) {
        analyticsProvider.onAdFailedToLoad(AdType.Interstitial, errorCode, errorMessage)
    }

    fun onImpression(adUnitId: String, network: String) {
        analyticsProvider.onAdImpression(AdType.Interstitial)
        revenueTracker.trackImpression(AdType.Interstitial, network, adUnitId)
    }

    fun onClicked(adUnitId: String, network: String) {
        analyticsProvider.onAdClicked(AdType.Interstitial)
        revenueTracker.trackClick(AdType.Interstitial, network, adUnitId)
    }

    fun onClosed() {
        analyticsProvider.onAdClosed(AdType.Interstitial)
    }

    fun onBlockedByPolicy(reason: String) {
        if (reason.contains("Cooldown", ignoreCase = true)) {
            analyticsProvider.onAdBlockedByFrequencyCap(AdType.Interstitial)
        } else if (reason.contains("disabled", ignoreCase = true)) {
            analyticsProvider.onAdBlockedByRemoteConfig(AdType.Interstitial)
        } else {
            // General policy block
        }
    }

    fun onPaidEvent(adUnitId: String, valueMicros: Long, currencyCode: String, network: String) {
        revenueTracker.trackPaidEvent(
            adType = AdType.Interstitial,
            valueMicros = valueMicros,
            currencyCode = currencyCode,
            network = network,
            placement = adUnitId
        )
    }
}
