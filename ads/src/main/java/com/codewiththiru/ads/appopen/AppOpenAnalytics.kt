package com.codewiththiru.ads.appopen

import com.codewiththiru.ads.analytics.AdsAnalyticsProvider
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.revenue.AdRevenueTracker

/**
 * Handles analytics and revenue tracking specific to App Open Ads.
 */
class AppOpenAnalytics(
    private val analyticsProvider: AdsAnalyticsProvider,
    private val revenueTracker: AdRevenueTracker
) {

    fun onColdStartRequest() {
        analyticsProvider.onAdRequested(AdType.AppOpen)
        // Additional cold_start property could be set in actual tracking
    }

    fun onWarmStartRequest() {
        analyticsProvider.onAdRequested(AdType.AppOpen)
        // Additional warm_start property could be set in actual tracking
    }

    fun onLoaded(loadTimeMs: Long) {
        analyticsProvider.onAdLoaded(AdType.AppOpen, loadTimeMs)
    }

    fun onFailedToLoad(errorCode: Int, errorMessage: String) {
        analyticsProvider.onAdFailedToLoad(AdType.AppOpen, errorCode, errorMessage)
    }

    fun onTimeoutReached() {
        // Log custom timeout event
        analyticsProvider.onAdFailedToLoad(AdType.AppOpen, -1, "Timeout reached")
    }

    fun onImpression(adUnitId: String, network: String) {
        analyticsProvider.onAdImpression(AdType.AppOpen)
        revenueTracker.trackImpression(AdType.AppOpen, network, adUnitId)
    }

    fun onClicked(adUnitId: String, network: String) {
        analyticsProvider.onAdClicked(AdType.AppOpen)
        revenueTracker.trackClick(AdType.AppOpen, network, adUnitId)
    }

    fun onClosed() {
        analyticsProvider.onAdClosed(AdType.AppOpen)
    }

    fun onPaidEvent(adUnitId: String, valueMicros: Long, currencyCode: String, network: String) {
        revenueTracker.trackPaidEvent(
            adType = AdType.AppOpen,
            valueMicros = valueMicros,
            currencyCode = currencyCode,
            network = network,
            placement = adUnitId
        )
    }
}
