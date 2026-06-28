package com.codewiththiru.platform.game.statistics.manager

import com.codewiththiru.platform.game.statistics.api.StatisticsProfile
import com.codewiththiru.platform.game.statistics.api.StatisticsProfileScope
import kotlinx.coroutines.flow.StateFlow

/**
 * Core interface for querying and managing game statistics.
 */
interface StatisticsManager {
    /**
     * Gets the full lifetime profile for a given game.
     */
    suspend fun getLifetimeProfile(gameId: String): StatisticsProfile

    /**
     * Gets the profile for a specific scope (e.g. DAILY).
     */
    suspend fun getProfile(
        gameId: String,
        scope: StatisticsProfileScope,
    ): StatisticsProfile

    /**
     * Returns a reactive flow of the lifetime profile to automatically update UIs.
     */
    fun observeLifetimeProfile(gameId: String): StateFlow<StatisticsProfile>

    /**
     * Returns a reactive flow of a specific scoped profile.
     */
    fun observeProfile(
        gameId: String,
        scope: StatisticsProfileScope,
    ): StateFlow<StatisticsProfile>

    /**
     * Resets statistics for a specific scope. Use with caution.
     */
    suspend fun resetStatistics(
        gameId: String,
        scope: StatisticsProfileScope,
    )
}
