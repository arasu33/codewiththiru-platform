package com.codewiththiru.platform.game.statistics.leaderboard

/**
 * Abstraction for syncing local scores with an external leaderboard service.
 */
interface LeaderboardManager {
    /**
     * Submits a score for the given game and leaderboard ID.
     */
    suspend fun submitScore(
        gameId: String,
        leaderboardId: String,
        score: Long,
    ): Boolean

    /**
     * Retrieves the current rank and top scores for a leaderboard.
     */
    suspend fun getLeaderboard(
        gameId: String,
        leaderboardId: String,
        limit: Int = 100,
    ): LeaderboardData
}

data class LeaderboardData(
    val leaderboardId: String,
    val topEntries: List<LeaderboardEntry>,
    val playerRank: Long?,
    val playerScore: Long?,
)

data class LeaderboardEntry(
    val rank: Long,
    val playerId: String,
    val score: Long,
    val displayName: String,
)
