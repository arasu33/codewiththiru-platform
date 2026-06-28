package com.codewiththiru.platform.game.leaderboard.testing

import com.codewiththiru.platform.game.leaderboard.api.LeaderboardEntry
import com.codewiththiru.platform.game.leaderboard.ranking.DefaultRankingEngine
import org.junit.Assert.assertEquals
import org.junit.Test

class LeaderboardTests {
    @Test
    fun `test ranking engine percentile calculation`() {
        val engine = DefaultRankingEngine()

        // Rank 10 out of 100 players means you beat 90 players. Percentile = 90.0%
        val top10Percent = engine.calculatePercentile(10, 100)
        assertEquals(90.0, top10Percent, 0.01)

        // Rank 1 out of 100 means you beat 99 players. Percentile = 99.0%
        val top1Percent = engine.calculatePercentile(1, 100)
        assertEquals(99.0, top1Percent, 0.01)

        // Rank 100 out of 100 means you beat 0 players. Percentile = 0.0%
        val bottomPercent = engine.calculatePercentile(100, 100)
        assertEquals(0.0, bottomPercent, 0.01)
    }

    @Test
    fun `test ranking engine sorting`() {
        val engine = DefaultRankingEngine()
        val p1 = LeaderboardEntry("1", "A", null, 50, 0, 0)
        val p2 = LeaderboardEntry("2", "B", null, 100, 0, 0)
        val p3 = LeaderboardEntry("3", "C", null, 25, 0, 0)

        val list = listOf(p1, p2, p3)

        val desc = engine.sortEntries(list, ascending = false)
        assertEquals("B", desc[0].displayName) // 100
        assertEquals("A", desc[1].displayName) // 50
        assertEquals("C", desc[2].displayName) // 25

        val asc = engine.sortEntries(list, ascending = true)
        assertEquals("C", asc[0].displayName) // 25
        assertEquals("A", asc[1].displayName) // 50
        assertEquals("B", asc[2].displayName) // 100
    }
}
