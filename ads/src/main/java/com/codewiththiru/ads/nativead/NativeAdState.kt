package com.codewiththiru.ads.nativead

/**
 * Encapsulates the Native Ad payload generically so the UI layer doesn't depend on AdMob directly.
 * The inner payload will typically be a Google Mobile Ads NativeAd object.
 */
data class NativeAdPayload(val adObject: Any)

/**
 * Represents the lifecycle state of a Native Ad request.
 */
sealed class NativeAdState {
    object Idle : NativeAdState()
    object Loading : NativeAdState()
    data class Loaded(val payload: NativeAdPayload) : NativeAdState()
    data class Error(val exception: Throwable) : NativeAdState()
}
