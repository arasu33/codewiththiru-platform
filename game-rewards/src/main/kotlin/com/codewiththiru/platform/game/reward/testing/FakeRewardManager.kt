package com.codewiththiru.platform.game.reward.testing

import com.codewiththiru.platform.game.reward.api.RewardBalance
import com.codewiththiru.platform.game.reward.api.RewardDefinition
import com.codewiththiru.platform.game.reward.api.RewardType
import com.codewiththiru.platform.game.reward.manager.RewardManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeRewardManager : RewardManager {
    private val balances = mutableMapOf<RewardType, Long>()
    private val balanceFlows = mutableMapOf<RewardType, MutableStateFlow<RewardBalance>>()
    private val inventory = mutableSetOf<String>()

    override suspend fun grantReward(
        reward: RewardDefinition,
        idempotencyKey: String,
    ): Boolean {
        val current = balances[reward.type] ?: 0L
        balances[reward.type] = current + reward.amount

        val newBalance = RewardBalance(reward.type, balances[reward.type]!!)
        if (!balanceFlows.containsKey(reward.type)) {
            balanceFlows[reward.type] = MutableStateFlow(newBalance)
        } else {
            balanceFlows[reward.type]!!.value = newBalance
        }
        return true
    }

    override suspend fun getBalance(type: RewardType): RewardBalance = RewardBalance(type, balances[type] ?: 0L)

    override fun observeBalance(type: RewardType): StateFlow<RewardBalance> {
        if (!balanceFlows.containsKey(type)) {
            balanceFlows[type] = MutableStateFlow(RewardBalance(type, balances[type] ?: 0L))
        }
        return balanceFlows[type]!!.asStateFlow()
    }

    override suspend fun hasInventoryItem(itemId: String): Boolean = inventory.contains(itemId)

    fun addItemToInventory(itemId: String) {
        inventory.add(itemId)
    }
}
