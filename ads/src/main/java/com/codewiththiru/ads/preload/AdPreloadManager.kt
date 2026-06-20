package com.codewiththiru.ads.preload

import com.codewiththiru.ads.api.AdType

/**
 * Manages the cache of preloaded ads to ensure warm startup and fast rendering.
 */
interface AdPreloadManager {

    /**
     * Triggers the preload sequence based on remote configuration.
     */
    suspend fun startPreloading()

    /**
     * Checks if a warm ad of the given [adType] is available.
     */
    fun isAdAvailable(adType: AdType): Boolean

    /**
     * Handles TTL expiration, potentially reloading an expired ad.
     */
    suspend fun checkExpirations()

    /**
     * Clears the current cache of preloaded ads.
     */
    fun clearCache()
}
