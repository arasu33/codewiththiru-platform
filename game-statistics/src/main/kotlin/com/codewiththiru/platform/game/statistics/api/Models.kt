package com.codewiththiru.platform.game.statistics.api

import kotlinx.serialization.Serializable

/**
 * Common metrics tracked across all games.
 */
enum class StatisticsMetric {
    GAMES_PLAYED,
    GAMES_COMPLETED,
    GAMES_WON,
    GAMES_LOST,
    GAMES_ABANDONED,
    CURRENT_STREAK,
    LONGEST_STREAK,
    FASTEST_COMPLETION_SECONDS,
    SLOWEST_COMPLETION_SECONDS,
    AVERAGE_COMPLETION_SECONDS,
    BEST_SCORE,
    AVERAGE_SCORE,
    HINTS_USED,
    UNDOS_USED,
    MOVES_MADE,
    MISTAKES_MADE,
    RETRIES,
    RESTARTS,
    SESSIONS_PLAYED,
    TOTAL_PLAY_TIME_SECONDS,
    PAUSE_TIME_SECONDS,
    RESUME_COUNT,
    PERFECT_GAMES,
    STARS_EARNED,
    XP_EARNED,
    COINS_EARNED,
}

/**
 * Represents the scope of the statistics (daily, weekly, lifetime, etc.).
 */
enum class StatisticsProfileScope {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    LIFETIME,
    CUSTOM,
}

/**
 * A profile of statistics for a particular scope.
 */
@Serializable
data class StatisticsProfile(
    val gameId: String,
    val scope: StatisticsProfileScope,
    val startTimeMs: Long,
    val endTimeMs: Long?,
    val metrics: Map<StatisticsMetric, Double> = emptyMap(),
)

/**
 * Represents a point-in-time calculation of statistics.
 */
@Serializable
data class StatisticsSnapshot(
    val timestampMs: Long,
    val profile: StatisticsProfile,
)

/**
 * Real-time active play tracking.
 */
@Serializable
data class StatisticsSession(
    val sessionId: String,
    val gameId: String,
    val startTimeMs: Long,
    val currentMetrics: Map<StatisticsMetric, Double> = emptyMap(),
)
