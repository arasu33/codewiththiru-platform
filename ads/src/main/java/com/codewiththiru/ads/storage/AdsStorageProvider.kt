package com.codewiththiru.ads.storage

import com.codewiththiru.ads.api.AdType

/**
 * Interface for persisting Ads-related data (e.g., using DataStore).
 */
interface AdsStorageProvider {

    /**
     * Saves the last impression timestamp for the given [adType].
     */
    suspend fun saveLastImpressionTime(adType: AdType, timestampMs: Long)

    /**
     * Retrieves the last impression timestamp for the given [adType].
     */
    suspend fun getLastImpressionTime(adType: AdType): Long

    /**
     * Increments the total impressions count for the given [adType].
     */
    suspend fun incrementImpressionCount(adType: AdType)

    /**
     * Retrieves the total impressions count for the given [adType].
     */
    suspend fun getImpressionCount(adType: AdType): Int

    /**
     * Increments the total revenue value in micros.
     */
    suspend fun addRevenueMicros(valueMicros: Long)

    /**
     * Retrieves the total revenue value in micros.
     */
    suspend fun getTotalRevenueMicros(): Long

    /**
     * Clears all stored Ad data.
     */
    suspend fun clear()
}
