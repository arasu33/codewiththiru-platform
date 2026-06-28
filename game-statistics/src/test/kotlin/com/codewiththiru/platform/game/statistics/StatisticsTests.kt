package com.codewiththiru.platform.game.statistics

import com.codewiththiru.platform.game.statistics.api.StatisticsMetric
import com.codewiththiru.platform.game.statistics.api.StatisticsProfileScope
import com.codewiththiru.platform.game.statistics.metrics.PerformanceMetrics
import com.codewiththiru.platform.game.statistics.testing.FakeStatisticsManager
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class StatisticsTests {
    @Test
    fun `test win rate calculation`() {
        val metrics =
            mapOf(
                StatisticsMetric.GAMES_PLAYED to 10.0,
                StatisticsMetric.GAMES_WON to 7.0,
            )
        val winRate = PerformanceMetrics.calculateWinRate(metrics)
        assertEquals(70.0, winRate, 0.01)
    }

    @Test
    fun `test win rate zero played`() {
        val metrics =
            mapOf(
                StatisticsMetric.GAMES_PLAYED to 0.0,
                StatisticsMetric.GAMES_WON to 0.0,
            )
        val winRate = PerformanceMetrics.calculateWinRate(metrics)
        assertEquals(0.0, winRate, 0.01)
    }

    @Test
    fun `test hint ratio calculation`() {
        val metrics =
            mapOf(
                StatisticsMetric.GAMES_PLAYED to 5.0,
                StatisticsMetric.HINTS_USED to 15.0,
            )
        val ratio = PerformanceMetrics.calculateHintRatio(metrics)
        assertEquals(3.0, ratio, 0.01)
    }

    @Test
    fun `test FakeStatisticsManager updates`() =
        runTest {
            val manager = FakeStatisticsManager()
            val gameId = "sudoku"

            var profile = manager.getLifetimeProfile(gameId)
            assertEquals(0, profile.metrics.size)

            manager.updateMetrics(gameId, StatisticsProfileScope.LIFETIME, mapOf(StatisticsMetric.GAMES_PLAYED to 5.0))

            profile = manager.getLifetimeProfile(gameId)
            assertEquals(5.0, profile.metrics[StatisticsMetric.GAMES_PLAYED])
        }
}
