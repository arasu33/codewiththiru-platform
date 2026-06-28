package com.codewiththiru.platform.game.reward.policy

import com.codewiththiru.platform.game.reward.api.RewardType

/**
 * Defines constraints on wallets and inventory.
 */
interface RewardPolicy {
    /**
     * The maximum amount of a specific currency a player can hold.
     * Returns null if no limit exists.
     */
    fun getMaxBalance(type: RewardType): Long?

    /**
     * Analyzes if a deposit would breach the max balance.
     * Returns the actual amount that can be deposited.
     */
    fun calculateAllowedDeposit(
        type: RewardType,
        currentBalance: Long,
        depositAmount: Long,
    ): Long {
        val max = getMaxBalance(type) ?: return depositAmount
        val spaceLeft = max - currentBalance
        return if (spaceLeft > 0) minOf(spaceLeft, depositAmount) else 0L
    }
}
