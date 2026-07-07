package com.codewiththiru.platform.developer.gametesting.fake

import com.codewiththiru.platform.core.result.CustResult
import com.codewiththiru.platform.game.leaderboard.api.LeaderboardEntry
import com.codewiththiru.platform.game.leaderboard.api.LeaderboardRank
import com.codewiththiru.platform.game.leaderboard.filters.LeaderboardFilter
import com.codewiththiru.platform.game.leaderboard.manager.LeaderboardManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A robust fake for the LeaderboardManager that simulates in-memory score tracking.
 */
class FakeLeaderboardManager : LeaderboardManager {
    private val scores = mutableMapOf<String, Long>()
    private val entriesFlow = MutableStateFlow<List<LeaderboardEntry>>(emptyList())

    override fun submitScore(
        leaderboardId: String,
        score: Long,
    ) {
        scores[leaderboardId] = score
    }

    override suspend fun getTopEntries(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): StateFlow<List<LeaderboardEntry>> = entriesFlow

    override suspend fun getPlayerRank(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): StateFlow<LeaderboardRank?> = MutableStateFlow(null)

    override suspend fun refresh(
        leaderboardId: String,
        filter: LeaderboardFilter,
    ): CustResult<Unit> = CustResult.Success(Unit)
}
