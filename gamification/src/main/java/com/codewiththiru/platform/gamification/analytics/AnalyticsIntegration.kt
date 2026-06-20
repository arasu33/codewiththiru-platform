package com.codewiththiru.platform.gamification.analytics

import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.gamification.api.GamificationEvent

public data class ProgressionMetrics(
    val userId: String,
    val totalPlayTimeSeconds: Long,
    val currentLevel: Int
)

public interface EngagementTracker {
    public suspend fun trackSessionStart(userId: String)
    public suspend fun trackSessionEnd(userId: String)
}

public interface RetentionTracker {
    public suspend fun trackDailyReturn(userId: String, streakDays: Int)
}

public class GamificationAnalyticsProvider(
    private val analyticsManager: AnalyticsManager
) {
    public suspend fun logGamificationEvent(event: GamificationEvent) {
        // Map GamificationEvent to global AnalyticsEvent and push to the Analytics module
        val eventName = when (event) {
            is GamificationEvent.LevelUp -> "gamification_level_up"
            is GamificationEvent.AchievementUnlocked -> "gamification_achievement_unlocked"
            is GamificationEvent.BadgeEarned -> "gamification_badge_earned"
            is GamificationEvent.StreakExtended -> "gamification_streak_extended"
            is GamificationEvent.QuestCompleted -> "gamification_quest_completed"
        }
        
        val eventMap = when (event) {
            is GamificationEvent.LevelUp -> mapOf("level" to event.newLevel, "threshold" to event.xpThreshold)
            is GamificationEvent.AchievementUnlocked -> mapOf("achievement_id" to event.achievementId)
            is GamificationEvent.BadgeEarned -> mapOf("badge_id" to event.badgeId)
            is GamificationEvent.StreakExtended -> mapOf("streak" to event.currentStreak)
            is GamificationEvent.QuestCompleted -> mapOf("quest_id" to event.questId)
        }

        analyticsManager.track(
            com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent(
                name = eventName,
                parameters = eventMap
            )
        )
    }
}
