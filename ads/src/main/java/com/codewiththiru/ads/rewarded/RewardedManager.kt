package com.codewiththiru.ads.rewarded

import com.codewiththiru.ads.api.AdsManager
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.repository.AdsRepository
import com.codewiththiru.ads.state.AdState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Interface representing callbacks for a Rewarded Ad display.
 */
interface RewardedCallback {
    fun onAdImpression()
    fun onAdClicked()
    fun onAdFailedToShow(error: Throwable)
    fun onUserEarnedReward(rewardItem: RewardItem)
    fun onAdDismissed(earnedReward: Boolean)
}

/**
 * Manager specifically designed for Rewarded Ads.
 * Handles the state machine of a user earning or abandoning a reward, 
 * and verifies the reward payload securely.
 */
class RewardedManager(
    private val repository: AdsRepository,
    private val analytics: RewardedAnalytics,
    private val verifier: RewardVerifier,
    private val coroutineScope: CoroutineScope
) {
    private val _rewardedState = MutableStateFlow<RewardedState>(RewardedState.Idle)
    val rewardedState: StateFlow<RewardedState> = _rewardedState.asStateFlow()

    private var hasEarnedRewardInCurrentSession = false

    /**
     * Loads a Rewarded Ad into memory.
     */
    fun load() {
        _rewardedState.value = RewardedState.Loading
        analytics.onRequested()
        coroutineScope.launch {
            repository.load(AdType.Rewarded)
            // Listen to the state to update local flow
            repository.observeState(AdType.Rewarded).collect { state ->
                when (state) {
                    is AdState.Loaded -> _rewardedState.value = RewardedState.Loaded
                    is AdState.Failed -> {
                        _rewardedState.value = RewardedState.Error(Exception(state.error.message))
                        analytics.onFailedToLoad(0, state.error.message ?: "Unknown error")
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * Displays a loaded Rewarded Ad.
     */
    suspend fun show(callback: RewardedCallback?) {
        hasEarnedRewardInCurrentSession = false

        if (_rewardedState.value !is RewardedState.Loaded) {
            val error = Exception("Rewarded ad is not ready to be shown")
            _rewardedState.value = RewardedState.Error(error)
            callback?.onAdFailedToShow(error)
            return
        }

        _rewardedState.value = RewardedState.Showing
        
        // Let the repository know we want to show the ad.
        // The actual implementation of bridging repository show callbacks 
        // will be done via a dedicated provider orchestrator or controller.
        try {
            repository.show(AdType.Rewarded)
            analytics.onImpression("rewarded_unit", "AdMob")
            callback?.onAdImpression()
        } catch (e: Exception) {
            _rewardedState.value = RewardedState.Error(e)
            callback?.onAdFailedToShow(e)
        }
    }

    /**
     * Called by the provider orchestrator when the user completes the video.
     */
    fun handleUserEarnedReward(rewardItem: RewardItem, callback: RewardedCallback?) {
        coroutineScope.launch {
            val isLegitimate = verifier.verify(rewardItem)
            if (isLegitimate) {
                hasEarnedRewardInCurrentSession = true
                _rewardedState.value = RewardedState.Earned(rewardItem.type, rewardItem.amount)
                analytics.onEarnedReward(rewardItem, "rewarded_unit")
                callback?.onUserEarnedReward(rewardItem)
            } else {
                val error = Exception("Reward verification failed")
                _rewardedState.value = RewardedState.Error(error)
                callback?.onAdFailedToShow(error)
            }
        }
    }

    /**
     * Called by the provider orchestrator when the ad is closed.
     */
    fun handleAdDismissed(callback: RewardedCallback?) {
        if (!hasEarnedRewardInCurrentSession) {
            _rewardedState.value = RewardedState.Abandoned
            analytics.onAbandoned()
        } else {
            _rewardedState.value = RewardedState.Idle
            analytics.onClosed()
        }
        callback?.onAdDismissed(hasEarnedRewardInCurrentSession)
        
        // Auto-preload the next ad
        load()
    }
}
