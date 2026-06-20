package com.codewiththiru.ads.banner

import com.codewiththiru.ads.api.AdsManager
import com.codewiththiru.ads.api.AdType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Controller to manage the lifecycle and state of a Banner Ad.
 */
class BannerController(
    private val adsManager: AdsManager,
    private val analytics: BannerAnalytics,
    private val adUnitId: String
) {
    private val _bannerState = MutableStateFlow<BannerState>(BannerState.Idle)
    val bannerState: StateFlow<BannerState> = _bannerState.asStateFlow()

    fun onLoading() {
        _bannerState.value = BannerState.Loading
        analytics.onBannerRequested(adUnitId)
    }

    fun onLoaded(loadTimeMs: Long) {
        _bannerState.value = BannerState.Loaded
        analytics.onBannerLoaded(adUnitId, loadTimeMs)
    }

    fun onFailed(errorCode: Int, errorMessage: String) {
        val exception = Exception(errorMessage)
        _bannerState.value = BannerState.Failed(exception)
        analytics.onBannerFailedToLoad(adUnitId, errorCode, errorMessage)
    }

    fun onImpression() {
        analytics.onBannerImpression(adUnitId)
    }

    fun onClicked() {
        analytics.onBannerClicked(adUnitId)
    }
}
