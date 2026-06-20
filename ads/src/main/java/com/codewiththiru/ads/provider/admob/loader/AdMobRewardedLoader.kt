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
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AdMobRewardedLoader(private val context: Context) {

    private var rewardedAd: RewardedAd? = null

    suspend fun load(adUnitId: String): AdLoadResult {
        val adRequest = AdRequest.Builder().build()
        val startTime = System.currentTimeMillis()

        return suspendCancellableCoroutine { continuation ->
            RewardedAd.load(
                context,
                adUnitId,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        rewardedAd = null
                        continuation.resume(
                            AdLoadResult.Failure(
                                AdType.Rewarded.name,
                                AdErrorMapper.mapLoadError(adError)
                            )
                        )
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        rewardedAd = ad
                        val loadTime = System.currentTimeMillis() - startTime
                        continuation.resume(AdLoadResult.Success(AdType.Rewarded.name, loadTime))
                    }
                }
            )
        }
    }

    suspend fun show(activity: Activity): AdShowResult {
        val ad = rewardedAd ?: return AdShowResult.Failure(
            AdType.Rewarded.name,
            IllegalStateException("Rewarded ad not loaded")
        )

        return suspendCancellableCoroutine { continuation ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    if (continuation.isActive) {
                        continuation.resume(AdShowResult.Dismissed(AdType.Rewarded.name))
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    rewardedAd = null
                    if (continuation.isActive) {
                        continuation.resume(
                            AdShowResult.Failure(
                                AdType.Rewarded.name,
                                AdErrorMapper.mapShowError(adError)
                            )
                        )
                    }
                }

                override fun onAdShowedFullScreenContent() {
                    // Note: We don't resume here immediately for Rewarded ads typically, 
                    // we usually wait for dismissal to return the full result, but for AdShowResult
                    // we just mark it as success. Actually, let's resume on dismiss or fail.
                    // Or we could return success here and handle rewards via a separate callback.
                    // For consistency with Interstitial, let's just return Success when it shows.
                    if (continuation.isActive) {
                        continuation.resume(AdShowResult.Success(AdType.Rewarded.name))
                    }
                }
            }
            
            ad.show(activity) { rewardItem ->
                // Handle reward. Usually we need an event bus or analytics hook here.
                // For now, this is internal to the loader and handled via AdsAnalytics downstream.
            }
        }
    }

    fun destroy() {
        rewardedAd?.fullScreenContentCallback = null
        rewardedAd = null
    }
}
