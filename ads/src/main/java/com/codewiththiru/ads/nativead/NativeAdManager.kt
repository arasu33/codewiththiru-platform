package com.codewiththiru.ads.nativead

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Interface that abstracts the loading of a Native Ad.
 */
interface NativeAdLoader {
    suspend fun load(adUnitId: String): NativeAdResult
}

sealed class NativeAdResult {
    data class Success(val payload: NativeAdPayload) : NativeAdResult()
    data class Failure(val error: Throwable) : NativeAdResult()
}

/**
 * Manages the fetching and state of Native Ads.
 * Designed to handle multiple ad requests (e.g., for a feed).
 */
class NativeAdManager(
    private val loader: NativeAdLoader,
    private val coroutineScope: CoroutineScope
) {
    /**
     * Loads a native ad and returns a StateFlow that emits the loading progress and result.
     * This allows a LazyColumn to request an ad and seamlessly observe its state.
     */
    fun loadAd(adUnitId: String): StateFlow<NativeAdState> {
        val stateFlow = MutableStateFlow<NativeAdState>(NativeAdState.Loading)
        
        coroutineScope.launch {
            when (val result = loader.load(adUnitId)) {
                is NativeAdResult.Success -> {
                    stateFlow.value = NativeAdState.Loaded(result.payload)
                }
                is NativeAdResult.Failure -> {
                    stateFlow.value = NativeAdState.Error(result.error)
                }
            }
        }
        
        return stateFlow.asStateFlow()
    }
}
