package com.codewiththiru.platform.gamification.season

public data class EventReward(
    val id: String,
    val xpMultiplier: Float = 1.0f,
    val grantedCoins: Long = 0L,
    val grantedBadgeId: String? = null
)

public data class LiveEvent(
    val id: String,
    val title: String,
    val startTime: Long,
    val endTime: Long,
    val rewards: List<EventReward>
)

public data class SeasonPass(
    val seasonId: String,
    val isPremiumUnlocked: Boolean,
    val currentTier: Int
)

public data class Season(
    val id: String,
    val title: String,
    val activeEvents: List<LiveEvent>
)

public interface SeasonManager {
    public fun getCurrentSeason(): Season?
    public suspend fun getSeasonPass(userId: String, seasonId: String): SeasonPass
}
