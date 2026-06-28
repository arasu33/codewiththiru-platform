package com.codewiththiru.platform.game.achievement

import com.codewiththiru.platform.game.achievement.conditions.CompositeCondition
import com.codewiththiru.platform.game.achievement.conditions.StatisticCondition
import com.codewiththiru.platform.game.statistics.api.StatisticsMetric
import com.codewiththiru.platform.game.statistics.api.StatisticsProfile
import com.codewiththiru.platform.game.statistics.api.StatisticsProfileScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AchievementTests {
    @Test
    fun `test StatisticCondition evaluate`() {
        val profile =
            StatisticsProfile(
                gameId = "test",
                scope = StatisticsProfileScope.LIFETIME,
                startTimeMs = 0L,
                endTimeMs = null,
                metrics = mapOf(StatisticsMetric.GAMES_WON to 5.0),
            )

        val conditionMet = StatisticCondition(StatisticsMetric.GAMES_WON, 5.0, isGreaterThan = true)
        val resultMet = conditionMet.evaluate(profile)
        assertTrue(resultMet.first)
        assertEquals(5.0, resultMet.second, 0.0)

        val conditionNotMet = StatisticCondition(StatisticsMetric.GAMES_WON, 10.0, isGreaterThan = true)
        val resultNotMet = conditionNotMet.evaluate(profile)
        assertFalse(resultNotMet.first)
        assertEquals(5.0, resultNotMet.second, 0.0)
    }

    @Test
    fun `test CompositeCondition AND`() {
        val profile =
            StatisticsProfile(
                gameId = "test",
                scope = StatisticsProfileScope.LIFETIME,
                startTimeMs = 0L,
                endTimeMs = null,
                metrics =
                    mapOf(
                        StatisticsMetric.GAMES_WON to 10.0,
                        StatisticsMetric.PERFECT_GAMES to 2.0,
                    ),
            )

        val cond1 = StatisticCondition(StatisticsMetric.GAMES_WON, 10.0)
        val cond2 = StatisticCondition(StatisticsMetric.PERFECT_GAMES, 1.0)

        val composite = CompositeCondition(listOf(cond1, cond2), requireAll = true)
        val result = composite.evaluate(profile)

        assertTrue(result.first)
    }

    @Test
    fun `test CompositeCondition OR`() {
        val profile =
            StatisticsProfile(
                gameId = "test",
                scope = StatisticsProfileScope.LIFETIME,
                startTimeMs = 0L,
                endTimeMs = null,
                metrics =
                    mapOf(
                        StatisticsMetric.GAMES_WON to 5.0,
                        StatisticsMetric.PERFECT_GAMES to 0.0,
                    ),
            )

        val cond1 = StatisticCondition(StatisticsMetric.GAMES_WON, 10.0) // Fail
        val cond2 = StatisticCondition(StatisticsMetric.PERFECT_GAMES, 0.0, isGreaterThan = false) // Pass

        val composite = CompositeCondition(listOf(cond1, cond2), requireAll = false)
        val result = composite.evaluate(profile)

        assertTrue(result.first)
    }
}
