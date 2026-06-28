package com.codewiththiru.platform.game.leaderboard.manager

import com.codewiththiru.platform.core.result.CustResult
import com.codewiththiru.platform.game.leaderboard.api.LeaderboardEntry
import com.codewiththiru.platform.game.leaderboard.api.LeaderboardRank
import com.codewiththiru.platform.game.leaderboard.filters.LeaderboardFilter
import kotlinx.coroutines.flow.StateFlow

/**
 * Top level orchestrator for Leaderboards.
 */
interface LeaderboardManager {
    /**
     * Enqueues a score submission. Utilizes the Sync Engine under the hood
     * if the device is currently offline.
     */
    fun submitScore(
        leaderboardId: String,
        score: Long,
    )

    /**
     * Fetches top entries with reactive caching.
     */
    suspend fun getTopEntries(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): StateFlow<List<LeaderboardEntry>>

    /**
     * Fetches the current player's rank.
     */
    suspend fun getPlayerRank(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): StateFlow<LeaderboardRank?>

    /**
     * Forces a network refresh of the leaderboard cache.
     */
    suspend fun refresh(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): CustResult<Unit>
}
