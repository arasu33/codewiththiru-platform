package com.codewiththiru.platform.game.reward

import com.codewiththiru.platform.game.reward.api.RewardDefinition
import com.codewiththiru.platform.game.reward.api.RewardSource
import com.codewiththiru.platform.game.reward.api.RewardType
import com.codewiththiru.platform.game.reward.multiplier.Multiplier
import com.codewiththiru.platform.game.reward.multiplier.RewardMultiplierEngine
import com.codewiththiru.platform.game.reward.policy.RewardPolicy
import com.codewiththiru.platform.game.reward.testing.FakeRewardManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class RewardTests {
    @Test
    fun `test FakeRewardManager grant and observe`() =
        runTest {
            val manager = FakeRewardManager()
            val reward = RewardDefinition("test_1", RewardType.COINS, 100L, RewardSource.GAME_COMPLETION)

            val success = manager.grantReward(reward, "tx_123")
            assertTrue(success)

            val balance = manager.getBalance(RewardType.COINS)
            assertEquals(100L, balance.totalAmount)
        }

    @Test
    fun `test Multiplier Engine compounding`() {
        val engine =
            object : RewardMultiplierEngine {
                override fun getActiveMultipliers(): List<Multiplier> =
                    listOf(
                        Multiplier("vip", 1.5, true),
                        Multiplier("weekend", 2.0, true),
                        Multiplier("inactive", 5.0, false),
                    )
            }

        val baseReward = RewardDefinition("test_2", RewardType.XP, 100L, RewardSource.DAILY_LOGIN)
        val finalReward = engine.applyMultipliers(baseReward)

        // 100 * 1.5 * 2.0 = 300
        assertEquals(300L, finalReward.amount)
    }

    @Test
    fun `test RewardPolicy max balance cap`() {
        val policy =
            object : RewardPolicy {
                override fun getMaxBalance(type: RewardType): Long? = if (type == RewardType.LIVES) 5L else null
            }

        // Current lives = 3, trying to deposit 4. Should only allow 2.
        val allowed = policy.calculateAllowedDeposit(RewardType.LIVES, 3L, 4L)
        assertEquals(2L, allowed)

        // Current coins = 100, trying to deposit 1000. Should allow 1000.
        val allowedCoins = policy.calculateAllowedDeposit(RewardType.COINS, 100L, 1000L)
        assertEquals(1000L, allowedCoins)
    }
}
