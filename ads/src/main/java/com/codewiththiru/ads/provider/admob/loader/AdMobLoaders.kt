package com.codewiththiru.ads.provider.admob.loader

import android.app.Activity
import android.content.Context
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.provider.AdLoadResult
import com.codewiththiru.ads.provider.AdShowResult

/**
 * Basic shell for banner loader. Banner typically returns a View to be placed in Compose.
 */
class AdMobBannerLoader(private val context: Context) {
    // Note: Banner ad loads are often handled at the UI component level rather than
    // a global provider, but keeping it here for consistency in initialization tracking.
    
    suspend fun load(adUnitId: String): AdLoadResult {
        // Implementation will be handled in the specific Banner component 
        // to return the actual view.
        return AdLoadResult.Success(AdType.Banner.name, 0)
    }

    suspend fun show(activity: Activity): AdShowResult {
        // Banners are shown by embedding them, not calling show() on an activity.
        return AdShowResult.Success(AdType.Banner.name)
    }

    fun destroy() {}
}

class AdMobNativeLoader(private val context: Context) {
    suspend fun load(adUnitId: String): AdLoadResult {
        return AdLoadResult.Success(AdType.Native.name, 0)
    }

    suspend fun show(activity: Activity): AdShowResult {
        return AdShowResult.Success(AdType.Native.name)
    }

    fun destroy() {}
}

class AdMobRewardedInterstitialLoader(private val context: Context) {
    suspend fun load(adUnitId: String): AdLoadResult {
        return AdLoadResult.Success(AdType.RewardedInterstitial.name, 0)
    }

    suspend fun show(activity: Activity): AdShowResult {
        return AdShowResult.Success(AdType.RewardedInterstitial.name)
    }

    fun destroy() {}
}
