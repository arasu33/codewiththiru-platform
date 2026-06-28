package com.codewiththiru.platform.game.reward.manager

import com.codewiththiru.platform.game.reward.api.RewardBalance
import com.codewiththiru.platform.game.reward.api.RewardDefinition
import com.codewiththiru.platform.game.reward.api.RewardType
import kotlinx.coroutines.flow.StateFlow

/**
 * The primary API for clients to interact with rewards, wallets, and inventories.
 */
interface RewardManager {
    /**
     * Submits a request to grant a reward to the player.
     */
    suspend fun grantReward(
        reward: RewardDefinition,
        idempotencyKey: String,
    ): Boolean

    /**
     * Gets the active balance of a currency.
     */
    suspend fun getBalance(type: RewardType): RewardBalance

    /**
     * Returns a reactive flow for a specific currency balance.
     */
    fun observeBalance(type: RewardType): StateFlow<RewardBalance>

    /**
     * Checks if the player possesses a specific unique item in their inventory.
     */
    suspend fun hasInventoryItem(itemId: String): Boolean
}
