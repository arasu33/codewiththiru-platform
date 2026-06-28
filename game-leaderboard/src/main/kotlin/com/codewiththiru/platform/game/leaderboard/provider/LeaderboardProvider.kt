package com.codewiththiru.platform.game.leaderboard.provider

import com.codewiththiru.platform.core.result.CustResult
import com.codewiththiru.platform.game.leaderboard.api.LeaderboardEntry
import com.codewiththiru.platform.game.leaderboard.api.LeaderboardRank
import com.codewiththiru.platform.game.leaderboard.filters.LeaderboardFilter

/**
 * Abstraction for any backend providing leaderboard capabilities.
 * Agnostic of Firebase / Play Games / Supabase.
 */
interface LeaderboardProvider {
    /**
     * Submits a score directly to the cloud.
     */
    suspend fun submitScore(
        leaderboardId: String,
        score: Long,
        metadata: String? = null,
    ): CustResult<Unit>

    /**
     * Fetches a paginated block of top entries based on filter parameters.
     */
    suspend fun fetchTopEntries(
        leaderboardId: String,
        filter: LeaderboardFilter,
        limit: Int = 100,
    ): CustResult<List<LeaderboardEntry>>

    /**
     * Fetches the specific rank and percentile of the current authenticated user.
     */
    suspend fun fetchPlayerRank(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): CustResult<LeaderboardRank>
}
