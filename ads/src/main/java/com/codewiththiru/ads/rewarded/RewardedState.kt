package com.codewiththiru.ads.rewarded

/**
 * Represents the discrete steps in a rewarded ad lifecycle.
 */
sealed class RewardedState {
    object Idle : RewardedState()
    object Loading : RewardedState()
    object Loaded : RewardedState()
    object Showing : RewardedState()
    
    /**
     * User completed the video/action and earned the reward.
     */
    data class Earned(val rewardType: String, val amount: Int) : RewardedState()
    
    /**
     * User closed the ad before completion.
     */
    object Abandoned : RewardedState()
    
    data class Error(val throwable: Throwable) : RewardedState()
}
