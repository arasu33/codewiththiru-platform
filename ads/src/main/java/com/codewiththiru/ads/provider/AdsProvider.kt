package com.codewiththiru.ads.provider

import android.app.Activity
import com.codewiththiru.ads.api.AdType

/**
 * Platform-agnostic interface for an Ad Provider (e.g., AdMob, AppLovin).
 */
interface AdsProvider {

    /**
     * Initializes the ad provider SDK.
     */
    suspend fun initialize()

    /**
     * Loads an ad for the specified [adType] using the resolved [adUnitId].
     */
    suspend fun load(adType: AdType, adUnitId: String): AdLoadResult

    /**
     * Shows an ad for the specified [adType] using the provided [activity] context.
     */
    suspend fun show(adType: AdType, activity: Activity): AdShowResult

    /**
     * Destroys resources associated with the specified [adType] to prevent memory leaks.
     */
    fun destroy(adType: AdType)
}
