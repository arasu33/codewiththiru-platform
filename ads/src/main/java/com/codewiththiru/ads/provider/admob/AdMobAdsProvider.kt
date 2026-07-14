package com.codewiththiru.ads.provider.admob

import android.app.Activity
import android.content.Context
import com.codewiththiru.ads.api.AdsEnvironment
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.config.AdsConfig
import com.codewiththiru.ads.provider.AdLoadResult
import com.codewiththiru.ads.provider.AdShowResult
import com.codewiththiru.ads.provider.AdsProvider
import com.codewiththiru.ads.provider.admob.loader.*
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

/**
 * AdMob implementation of [AdsProvider]. Orchestrates loading and showing via specific format loaders.
 */
class AdMobAdsProvider(
    private val context: Context,
    private val environment: AdsEnvironment,
    private val config: AdsConfig
) : AdsProvider {

    private val interstitialLoader = AdMobInterstitialLoader(context)
    private val rewardedLoader = AdMobRewardedLoader(context)
    private val appOpenLoader = AdMobAppOpenLoader(context)
    private val bannerLoader = AdMobBannerLoader(context)
    private val nativeLoader = AdMobNativeLoader(context)
    private val rewardedInterstitialLoader = AdMobRewardedInterstitialLoader(context)

    override suspend fun initialize() {
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                android.content.pm.PackageManager.GET_META_DATA
            )
            val appId = appInfo.metaData?.getString("com.google.android.gms.ads.APPLICATION_ID")
            if (appId.isNullOrEmpty()) {
                android.util.Log.e(
                    "CWT_PLATFORM",
                    "AdMob initialization skipped: 'com.google.android.gms.ads.APPLICATION_ID' metadata tag is missing or empty in AndroidManifest.xml"
                )
                return
            }

            if (environment.isTestEnvironment || config.testMode) {
                val requestConfiguration = RequestConfiguration.Builder()
                    .setTestDeviceIds(listOf(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR))
                    .build()
                MobileAds.setRequestConfiguration(requestConfiguration)
            }
            
            MobileAds.initialize(context) { status ->
                // Diagnostics can be collected from this status if needed
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            android.util.Log.e("CWT_PLATFORM", "Failed to initialize MobileAds SDK gracefully", e)
        }
    }

    override suspend fun load(adType: AdType, adUnitId: String): AdLoadResult {
        return when (adType) {
            AdType.Interstitial -> interstitialLoader.load(adUnitId)
            AdType.Rewarded -> rewardedLoader.load(adUnitId)
            AdType.AppOpen -> appOpenLoader.load(adUnitId)
            AdType.Banner, AdType.AdaptiveBanner -> bannerLoader.load(adUnitId)
            AdType.Native -> nativeLoader.load(adUnitId)
            AdType.RewardedInterstitial -> rewardedInterstitialLoader.load(adUnitId)
        }
    }

    override suspend fun show(adType: AdType, activity: Activity): AdShowResult {
        return when (adType) {
            AdType.Interstitial -> interstitialLoader.show(activity)
            AdType.Rewarded -> rewardedLoader.show(activity)
            AdType.AppOpen -> appOpenLoader.show(activity)
            AdType.Banner, AdType.AdaptiveBanner -> bannerLoader.show(activity)
            AdType.Native -> nativeLoader.show(activity)
            AdType.RewardedInterstitial -> rewardedInterstitialLoader.show(activity)
        }
    }

    override fun destroy(adType: AdType) {
        when (adType) {
            AdType.Interstitial -> interstitialLoader.destroy()
            AdType.Rewarded -> rewardedLoader.destroy()
            AdType.AppOpen -> appOpenLoader.destroy()
            AdType.Banner, AdType.AdaptiveBanner -> bannerLoader.destroy()
            AdType.Native -> nativeLoader.destroy()
            AdType.RewardedInterstitial -> rewardedInterstitialLoader.destroy()
        }
    }
}
