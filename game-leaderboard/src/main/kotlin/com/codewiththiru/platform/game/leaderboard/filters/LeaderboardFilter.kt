package com.codewiththiru.platform.game.leaderboard.filters

import kotlinx.serialization.Serializable

@Serializable
enum class TimeFilter {
    ALL_TIME,
    DAILY,
    WEEKLY,
    MONTHLY,
    SEASON,
}

/**
 * Serializable state defining the constraints of a leaderboard query.
 */
@Serializable
data class LeaderboardFilter(
    val timeFilter: TimeFilter = TimeFilter.ALL_TIME,
    val countryCode: String? = null,
    val gameMode: String? = null,
    val difficulty: String? = null,
)
