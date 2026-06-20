package com.codewiththiru.ads.api

import kotlinx.coroutines.flow.StateFlow
import com.codewiththiru.ads.state.AdState

/**
 * Public SDK entry point for Ads.
 */
interface AdsManager {
    
    /**
     * Initializes the Ads SDK.
     */
    suspend fun initialize()

    /**
     * Loads an ad for the specified [adType].
     */
    fun load(adType: AdType)

    /**
     * Preloads ads based on remote configuration.
     */
    fun preload()

    /**
     * Shows an ad for the specified [adType].
     */
    fun show(adType: AdType)

    /**
     * Destroys resources and cleans up memory.
     */
    fun destroy()

    /**
     * Refreshes ads configuration and state.
     */
    fun refresh()

    /**
     * Resets the Ads manager.
     */
    fun reset()

    /**
     * Observes the state of a specific ad type.
     */
    fun observeState(adType: AdType): StateFlow<AdState>
}
