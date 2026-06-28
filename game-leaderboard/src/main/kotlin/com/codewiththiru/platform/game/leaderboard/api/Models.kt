package com.codewiththiru.platform.game.leaderboard.api

import kotlinx.serialization.Serializable

@Serializable
enum class LeaderboardType {
    GLOBAL,
    COUNTRY,
    REGION,
    FRIENDS,
    NEARBY,
    CUSTOM,
}

@Serializable
data class LeaderboardEntry(
    val playerId: String,
    val displayName: String,
    val avatarUrl: String?,
    val score: Long,
    val rank: Long,
    val timestampMs: Long,
)

@Serializable
data class LeaderboardRank(
    val currentRank: Long,
    val percentile: Double,
    val totalPlayers: Long,
)

sealed class LeaderboardEvent {
    data class LeaderboardOpened(val leaderboardId: String, val type: LeaderboardType) : LeaderboardEvent()

    data class LeaderboardRefreshed(val leaderboardId: String, val type: LeaderboardType) : LeaderboardEvent()

    data class RankChanged(val leaderboardId: String, val oldRank: Long, val newRank: Long) : LeaderboardEvent()

    data class ScoreSubmitted(val leaderboardId: String, val score: Long) : LeaderboardEvent()
}
