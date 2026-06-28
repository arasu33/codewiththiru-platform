package com.codewiththiru.platform.game.reward.wallet

import com.codewiththiru.platform.game.reward.api.RewardBalance
import com.codewiththiru.platform.game.reward.api.RewardDefinition
import com.codewiththiru.platform.game.reward.api.RewardLedger
import com.codewiththiru.platform.game.reward.api.RewardType

/**
 * Handles transactional operations for countable currencies (Coins, Stars, XP).
 */
interface RewardWallet {
    /**
     * Deposits a reward into the wallet.
     * Returns true if successful, false if it hit a policy cap.
     */
    suspend fun deposit(reward: RewardDefinition): Boolean

    /**
     * Withdraws the specified amount.
     * Returns true if sufficient funds exist and were removed, false otherwise.
     */
    suspend fun withdraw(
        type: RewardType,
        amount: Long,
    ): Boolean

    /**
     * Retrieves the current balance for a specific currency.
     */
    suspend fun getBalance(type: RewardType): RewardBalance

    /**
     * Retrieves the full audit history.
     */
    suspend fun getLedger(): List<RewardLedger>
}
