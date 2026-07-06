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
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AdMobInterstitialLoader(private val context: Context) {

    private var interstitialAd: InterstitialAd? = null

    suspend fun load(adUnitId: String): AdLoadResult {
        val adRequest = AdRequest.Builder().build()
        val startTime = System.currentTimeMillis()

        return suspendCancellableCoroutine { continuation ->
            InterstitialAd.load(
                context,
                adUnitId,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        interstitialAd = null
                        continuation.resume(
                            AdLoadResult.Failure(
                                AdType.Interstitial.name,
                                AdErrorMapper.mapLoadError(adError)
                            )
                        )
                    }

                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                        val loadTime = System.currentTimeMillis() - startTime
                        continuation.resume(AdLoadResult.Success(AdType.Interstitial.name, loadTime))
                    }
                }
            )
        }
    }

    suspend fun show(activity: Activity): AdShowResult {
        val ad = interstitialAd ?: return AdShowResult.Failure(
            AdType.Interstitial.name,
            IllegalStateException("Interstitial ad not loaded")
        )

        return suspendCancellableCoroutine { continuation ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    if (continuation.isActive) {
                        continuation.resume(AdShowResult.Dismissed(AdType.Interstitial.name))
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                    if (continuation.isActive) {
                        continuation.resume(
                            AdShowResult.Failure(
                                AdType.Interstitial.name,
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
        interstitialAd?.fullScreenContentCallback = null
        interstitialAd = null
    }
}
