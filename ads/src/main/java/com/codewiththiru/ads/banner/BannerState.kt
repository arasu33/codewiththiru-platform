package com.codewiththiru.ads.banner

/**
 * Represents the UI state of a banner ad.
 */
sealed class BannerState {
    object Idle : BannerState()
    object Loading : BannerState()
    object Loaded : BannerState()
    data class Failed(val error: Throwable) : BannerState()
}
