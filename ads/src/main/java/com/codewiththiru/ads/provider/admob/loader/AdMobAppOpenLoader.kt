package com.codewiththiru.ads.provider.admob.loader

import android.app.Activity
import android.content.Context
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.provider.AdLoadResult
import com.codewiththiru.ads.provider.AdShowResult
import com.codewiththiru.ads.provider.admob.AdErrorMapper
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AdMobAppOpenLoader(private val context: Context) {

    private var appOpenAd: AppOpenAd? = null

    suspend fun load(adUnitId: String): AdLoadResult {
        val adRequest = AdRequest.Builder().build()
        val startTime = System.currentTimeMillis()

        return suspendCancellableCoroutine { continuation ->
            AppOpenAd.load(
                context,
                adUnitId,
                adRequest,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        appOpenAd = null
                        continuation.resume(
                            AdLoadResult.Failure(
                                AdType.AppOpen.name,
                                AdErrorMapper.mapLoadError(adError)
                            )
                        )
                    }

                    override fun onAdLoaded(ad: AppOpenAd) {
                        appOpenAd = ad
                        val loadTime = System.currentTimeMillis() - startTime
                        continuation.resume(AdLoadResult.Success(AdType.AppOpen.name, loadTime))
                    }
                }
            )
        }
    }

    suspend fun show(activity: Activity): AdShowResult {
        val ad = appOpenAd ?: return AdShowResult.Failure(
            AdType.AppOpen.name,
            IllegalStateException("AppOpen ad not loaded")
        )

        return suspendCancellableCoroutine { continuation ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    if (continuation.isActive) {
                        continuation.resume(AdShowResult.Dismissed(AdType.AppOpen.name))
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    appOpenAd = null
                    if (continuation.isActive) {
                        continuation.resume(
                            AdShowResult.Failure(
                                AdType.AppOpen.name,
                                AdErrorMapper.mapShowError(adError)
                            )
                        )
                    }
                }

                override fun onAdShowedFullScreenContent() {
                    // Do nothing here to keep the coroutine suspended until the ad is dismissed.
                }
            }
            ad.show(activity)
        }
    }

    fun destroy() {
        appOpenAd?.fullScreenContentCallback = null
        appOpenAd = null
    }
}
