package com.codewiththiru.ads.analytics

import com.codewiththiru.ads.api.AdType
import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Concrete implementation that maps ad events to the centralized AnalyticsManager.
 */
class PlatformAdsAnalyticsProvider(
    private val analyticsManager: AnalyticsManager,
    private val coroutineScope: CoroutineScope
) : AdsAnalyticsProvider {

    override fun onAdRequested(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_REQUESTED, mapOf("ad_type" to adType.name))
    }

    override fun onAdLoaded(adType: AdType, loadTimeMs: Long) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_LOADED,
            mapOf("ad_type" to adType.name, "load_time_ms" to loadTimeMs)
        )
    }

    override fun onAdFailedToLoad(adType: AdType, errorCode: Int, errorMessage: String) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_FAILED,
            mapOf(
                "ad_type" to adType.name,
                "error_code" to errorCode,
                "error_message" to errorMessage
            )
        )
    }

    override fun onAdImpression(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_IMPRESSION, mapOf("ad_type" to adType.name))
    }

    override fun onAdClicked(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_CLICKED, mapOf("ad_type" to adType.name))
    }

    override fun onAdOpened(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_OPENED, mapOf("ad_type" to adType.name))
    }

    override fun onAdClosed(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_CLOSED, mapOf("ad_type" to adType.name))
    }

    override fun onAdRewarded(adType: AdType, rewardType: String, amount: Int) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_REWARDED,
            mapOf(
                "ad_type" to adType.name,
                "reward_type" to rewardType,
                "reward_amount" to amount
            )
        )
    }

    override fun onAdBlockedByConsent(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_CONSENT_BLOCKED, mapOf("ad_type" to adType.name))
    }

    override fun onAdBlockedByFrequencyCap(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_CAP_BLOCKED, mapOf("ad_type" to adType.name))
    }

    override fun onAdBlockedByRemoteConfig(adType: AdType) {
        logEvent(AdsAnalyticsProvider.EVENT_AD_REMOTE_CONFIG_BLOCKED, mapOf("ad_type" to adType.name))
    }

    private fun logEvent(eventName: String, parameters: Map<String, Any>) {
        coroutineScope.launch {
            analyticsManager.track(AnalyticsEvent(eventName, parameters))
        }
    }
}
