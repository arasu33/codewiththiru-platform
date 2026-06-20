package com.codewiththiru.ads.banner

import com.codewiththiru.ads.analytics.AdsAnalyticsProvider
import com.codewiththiru.ads.revenue.AdRevenueTracker
import com.google.android.gms.ads.AdValue

/**
 * Handles analytics and revenue tracking for Banner Ads.
 */
class BannerAnalytics(
    private val analyticsProvider: AdsAnalyticsProvider,
    private val revenueTracker: AdRevenueTracker
) {

    fun onBannerRequested(adUnitId: String) {
        analyticsProvider.onAdRequested(com.codewiththiru.ads.api.AdType.Banner)
    }

    fun onBannerLoaded(adUnitId: String, loadTimeMs: Long) {
        analyticsProvider.onAdLoaded(com.codewiththiru.ads.api.AdType.Banner, loadTimeMs)
    }

    fun onBannerFailedToLoad(adUnitId: String, errorCode: Int, errorMessage: String) {
        analyticsProvider.onAdFailedToLoad(com.codewiththiru.ads.api.AdType.Banner, errorCode, errorMessage)
    }

    fun onBannerImpression(adUnitId: String) {
        analyticsProvider.onAdImpression(com.codewiththiru.ads.api.AdType.Banner)
        revenueTracker.trackImpression(com.codewiththiru.ads.api.AdType.Banner, "AdMob", adUnitId)
    }

    fun onBannerClicked(adUnitId: String) {
        analyticsProvider.onAdClicked(com.codewiththiru.ads.api.AdType.Banner)
        revenueTracker.trackClick(com.codewiththiru.ads.api.AdType.Banner, "AdMob", adUnitId)
    }

    fun onPaidEvent(adUnitId: String, adValue: AdValue) {
        revenueTracker.trackPaidEvent(
            adType = com.codewiththiru.ads.api.AdType.Banner,
            valueMicros = adValue.valueMicros,
            currencyCode = adValue.currencyCode,
            network = "AdMob",
            placement = adUnitId
        )
    }
}
