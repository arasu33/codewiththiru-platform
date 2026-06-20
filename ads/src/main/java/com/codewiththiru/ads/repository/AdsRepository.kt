package com.codewiththiru.ads.repository

import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.state.AdState
import kotlinx.coroutines.flow.StateFlow

/**
 * Orchestrates providers, cache management, state transitions, frequency checks, 
 * consent checks, and handles revenue/analytics hooks.
 */
interface AdsRepository {

    /**
     * Initializes the repository and underlying providers.
     */
    suspend fun initialize()

    /**
     * Loads an ad for the given type.
     */
    suspend fun load(adType: AdType)

    /**
     * Shows the ad if available and permitted.
     */
    suspend fun show(adType: AdType)

    /**
     * Obtains the observable state for a given ad type.
     */
    fun observeState(adType: AdType): StateFlow<AdState>

    /**
     * Clears internal state and caches.
     */
    fun clearCache()
}
