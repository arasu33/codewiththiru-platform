package com.codewiththiru.ads.rewarded

import com.codewiththiru.ads.analytics.AdsAnalyticsProvider
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.revenue.AdRevenueTracker

/**
 * Handles analytics and revenue tracking specific to Rewarded Ads.
 */
class RewardedAnalytics(
    private val analyticsProvider: AdsAnalyticsProvider,
    private val revenueTracker: AdRevenueTracker
) {

    fun onRequested() {
        analyticsProvider.onAdRequested(AdType.Rewarded)
    }

    fun onLoaded(loadTimeMs: Long) {
        analyticsProvider.onAdLoaded(AdType.Rewarded, loadTimeMs)
    }

    fun onFailedToLoad(errorCode: Int, errorMessage: String) {
        analyticsProvider.onAdFailedToLoad(AdType.Rewarded, errorCode, errorMessage)
    }

    fun onImpression(adUnitId: String, network: String) {
        analyticsProvider.onAdImpression(AdType.Rewarded)
        revenueTracker.trackImpression(AdType.Rewarded, network, adUnitId)
    }

    fun onClicked(adUnitId: String, network: String) {
        analyticsProvider.onAdClicked(AdType.Rewarded)
        revenueTracker.trackClick(AdType.Rewarded, network, adUnitId)
    }

    fun onEarnedReward(rewardItem: RewardItem, adUnitId: String) {
        analyticsProvider.onAdRewarded(AdType.Rewarded, rewardItem.type, rewardItem.amount)
        revenueTracker.trackReward(AdType.Rewarded, rewardItem.type, rewardItem.amount, rewardItem.network, adUnitId)
    }

    fun onAbandoned() {
        analyticsProvider.onAdClosed(AdType.Rewarded)
        // Additional custom tracking for abandonment could go here
    }

    fun onClosed() {
        analyticsProvider.onAdClosed(AdType.Rewarded)
    }

    fun onPaidEvent(adUnitId: String, valueMicros: Long, currencyCode: String, network: String) {
        revenueTracker.trackPaidEvent(
            adType = AdType.Rewarded,
            valueMicros = valueMicros,
            currencyCode = currencyCode,
            network = network,
            placement = adUnitId
        )
    }
}
