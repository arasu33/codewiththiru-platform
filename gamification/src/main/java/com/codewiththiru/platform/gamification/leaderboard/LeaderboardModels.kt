package com.codewiththiru.platform.gamification.leaderboard

public enum class LeaderboardType {
    GLOBAL, FRIENDS, WEEKLY, SEASONAL
}

public data class LeaderboardEntry(
    val userId: String,
    val displayName: String,
    val score: Long,
    val rank: Int
)

public data class Leaderboard(
    val id: String,
    val type: LeaderboardType,
    val entries: List<LeaderboardEntry>
)

public interface RankingEngine {
    public fun calculateRank(score: Long, totalPlayers: Int): Int
}

public interface LeaderboardManager {
    public suspend fun getLeaderboard(type: LeaderboardType): Leaderboard
    public suspend fun submitScore(userId: String, score: Long)
    public suspend fun getUserRank(userId: String, type: LeaderboardType): LeaderboardEntry?
}
