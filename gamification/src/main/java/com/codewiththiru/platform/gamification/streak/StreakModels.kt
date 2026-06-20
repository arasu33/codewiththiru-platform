package com.codewiththiru.platform.gamification.streak

public data class StreakPolicy(
    val maxFreezesAllowed: Int = 2,
    val requiresActionWithinHours: Int = 48,
    val rewardPerDay: Long = 10L
)

public data class StreakFreeze(
    val remainingFreezes: Int,
    val lastUsedTimestamp: Long?
)

public data class StreakReward(
    val xpBonus: Long,
    val coinBonus: Long
)

public data class Streak(
    val userId: String,
    val currentStreakDays: Int,
    val highestStreakDays: Int,
    val lastActionTimestamp: Long,
    val freezes: StreakFreeze
)

public interface StreakManager {
    public suspend fun registerAction(userId: String): Streak
    public suspend fun getStreak(userId: String): Streak
    public suspend fun applyFreeze(userId: String): Boolean
}
