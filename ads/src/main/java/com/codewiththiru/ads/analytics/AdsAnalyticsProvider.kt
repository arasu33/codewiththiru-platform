package com.codewiththiru.ads.analytics

import com.codewiththiru.ads.api.AdType

/**
 * Interface for logging standard ad-related events to an analytics service.
 */
interface AdsAnalyticsProvider {

    fun onAdRequested(adType: AdType)
    fun onAdLoaded(adType: AdType, loadTimeMs: Long)
    fun onAdFailedToLoad(adType: AdType, errorCode: Int, errorMessage: String)
    fun onAdImpression(adType: AdType)
    fun onAdClicked(adType: AdType)
    fun onAdOpened(adType: AdType)
    fun onAdClosed(adType: AdType)
    fun onAdRewarded(adType: AdType, rewardType: String, amount: Int)
    
    // Blocking Events
    fun onAdBlockedByConsent(adType: AdType)
    fun onAdBlockedByFrequencyCap(adType: AdType)
    fun onAdBlockedByRemoteConfig(adType: AdType)

    companion object {
        const val EVENT_AD_REQUESTED = "ad_requested"
        const val EVENT_AD_LOADED = "ad_loaded"
        const val EVENT_AD_FAILED = "ad_failed"
        const val EVENT_AD_IMPRESSION = "ad_impression"
        const val EVENT_AD_CLICKED = "ad_clicked"
        const val EVENT_AD_OPENED = "ad_opened"
        const val EVENT_AD_CLOSED = "ad_closed"
        const val EVENT_AD_REWARDED = "ad_rewarded"
        const val EVENT_AD_REVENUE = "ad_revenue"
        const val EVENT_AD_CAP_BLOCKED = "ad_cap_blocked"
        const val EVENT_AD_CONSENT_BLOCKED = "ad_consent_blocked"
        const val EVENT_AD_REMOTE_CONFIG_BLOCKED = "ad_remote_config_blocked"
    }
}
