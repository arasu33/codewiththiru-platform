package com.codewiththiru.platform.game.leaderboard.ranking

import com.codewiththiru.platform.game.leaderboard.api.LeaderboardEntry

/**
 * Interface responsible for calculating percentiles and sorting entries locally.
 */
interface RankingEngine {
    /**
     * Sorts the provided entries based on ascending or descending rules.
     */
    fun sortEntries(
        entries: List<LeaderboardEntry>,
        ascending: Boolean,
    ): List<LeaderboardEntry>

    /**
     * Returns a percentile score (e.g. 95.0 = Top 5%) given a rank and total population.
     */
    fun calculatePercentile(
        rank: Long,
        totalPlayers: Long,
    ): Double
}

class DefaultRankingEngine : RankingEngine {
    override fun sortEntries(
        entries: List<LeaderboardEntry>,
        ascending: Boolean,
    ): List<LeaderboardEntry> =
        if (ascending) {
            entries.sortedBy { it.score }
        } else {
            entries.sortedByDescending { it.score }
        }

    override fun calculatePercentile(
        rank: Long,
        totalPlayers: Long,
    ): Double {
        if (totalPlayers <= 0) return 0.0
        if (rank <= 0) return 100.0
        val percentage = (rank.toDouble() / totalPlayers.toDouble()) * 100.0
        return 100.0 - percentage
    }
}
