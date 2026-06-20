package com.codewiththiru.ads.interstitial

import com.codewiththiru.ads.api.AdsManager
import com.codewiththiru.ads.provider.AdShowResult

/**
 * Interface representing the callback events for an Interstitial Ad display.
 */
interface InterstitialCallback {
    fun onAdImpression()
    fun onAdClicked()
    fun onAdDismissed()
    fun onAdFailedToShow(error: Throwable)
}

/**
 * Controller to manage the lifecycle of a displayed Interstitial Ad.
 */
class InterstitialController(
    private val analytics: InterstitialAnalytics,
    private val adUnitId: String,
    private val network: String
) {

    fun handleShowResult(result: AdShowResult, callback: InterstitialCallback?) {
        when (result) {
            is AdShowResult.Success -> {
                analytics.onImpression(adUnitId, network)
                callback?.onAdImpression()
            }
            is AdShowResult.Failure -> {
                val errorCode = (result.error as? com.codewiththiru.ads.provider.admob.AdsException)?.errorCode ?: 0
                analytics.onFailedToLoad(errorCode, result.error.message ?: "Unknown show error")
                callback?.onAdFailedToShow(result.error)
            }
            is AdShowResult.Dismissed -> {
                onAdDismissed(callback)
            }
        }
    }

    fun onAdClicked(callback: InterstitialCallback?) {
        analytics.onClicked(adUnitId, network)
        callback?.onAdClicked()
    }

    fun onAdDismissed(callback: InterstitialCallback?) {
        analytics.onClosed()
        callback?.onAdDismissed()
    }
}
