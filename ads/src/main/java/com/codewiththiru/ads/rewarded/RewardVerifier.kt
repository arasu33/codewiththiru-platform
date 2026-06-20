package com.codewiththiru.ads.rewarded

/**
 * Encapsulates the reward details given by an ad network.
 */
data class RewardItem(
    val type: String,
    val amount: Int,
    val network: String = "AdMob",
    val signature: String? = null // For SSV validation
)

/**
 * Interface to verify a reward before granting it to the user.
 * Built to be SSV-ready.
 */
interface RewardVerifier {
    /**
     * Checks if the reward is legitimate.
     * Can be a simple local check or a suspendable network call to a backend.
     */
    suspend fun verify(reward: RewardItem): Boolean
}

/**
 * Default local implementation that blindly trusts the network callback.
 * Use for MVP before SSV is set up.
 */
class LocalRewardVerifier : RewardVerifier {
    override suspend fun verify(reward: RewardItem): Boolean {
        // In a real SSV implementation, we'd make a network call here
        // verifying the `reward.signature`.
        return reward.amount > 0
    }
}
