package com.codewiththiru.platform.gamification.achievement

public enum class AchievementCategory {
    GENERAL, LEARNING, SOCIAL, COMMERCE, SECRET
}

public data class AchievementReward(
    val xp: Long = 0L,
    val coins: Long = 0L,
    val gems: Long = 0L,
    val specialBadgeId: String? = null
)

public data class Achievement(
    val id: String,
    val category: AchievementCategory,
    val title: String,
    val description: String,
    val maxProgress: Int = 1,
    val isHidden: Boolean = false,
    val reward: AchievementReward = AchievementReward()
)

public data class AchievementProgress(
    val achievementId: String,
    val currentProgress: Int,
    val isUnlocked: Boolean,
    val unlockedTimestamp: Long?
)

public interface AchievementManager {
    public fun getAchievementDefinition(id: String): Achievement?
    public suspend fun getProgress(userId: String, achievementId: String): AchievementProgress
    public suspend fun incrementProgress(userId: String, achievementId: String, amount: Int = 1): Boolean
}
