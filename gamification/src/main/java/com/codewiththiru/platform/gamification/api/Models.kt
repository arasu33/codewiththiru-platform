package com.codewiththiru.platform.gamification.api

import kotlinx.serialization.Serializable

/**
 * Base representation of gamification results returned to clients.
 */
public sealed class GamificationResult<out T> {
    public data class Success<T>(val data: T) : GamificationResult<T>()
    public data class Error(val throwable: Throwable, val code: String? = null) : GamificationResult<Nothing>()
}

/**
 * Common payload for any gamification state (XP, Badges, etc.) that can be synced.
 */
@Serializable
public data class GamificationState(
    val userId: String,
    val totalXp: Long,
    val currentLevel: Int,
    val coinBalance: Long,
    val gemBalance: Long,
    val lastSyncTimestamp: Long
)

/**
 * Environmental context for executing gamification rules (e.g. active multipliers, offline status).
 */
public data class GamificationEnvironment(
    val isOffline: Boolean,
    val globalXpMultiplier: Float = 1.0f,
    val activeEventId: String? = null
)

/**
 * Emitted globally when significant gamification thresholds are crossed.
 */
public sealed class GamificationEvent {
    public data class LevelUp(val newLevel: Int, val xpThreshold: Long) : GamificationEvent()
    public data class AchievementUnlocked(val achievementId: String) : GamificationEvent()
    public data class BadgeEarned(val badgeId: String) : GamificationEvent()
    public data class StreakExtended(val currentStreak: Int) : GamificationEvent()
    public data class QuestCompleted(val questId: String) : GamificationEvent()
}
