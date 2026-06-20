package com.codewiththiru.platform.gamification.level

/**
 * Defines a Level within the Gamification system.
 */
public data class LevelDefinition(
    val levelNumber: Int,
    val requiredXp: Long,
    val title: String,
    val rewardCoins: Long = 0L,
    val rewardGems: Long = 0L
)

/**
 * Represents a user's progress towards the next level.
 */
public data class LevelProgress(
    val currentLevel: Int,
    val currentLevelTitle: String,
    val currentXpInLevel: Long,
    val xpRequiredForNextLevel: Long,
    val percentageComplete: Float
)

/**
 * Manages calculating and unlocking levels based on XP.
 */
public interface LevelManager {
    public fun getLevelDefinition(level: Int): LevelDefinition
    public fun calculateLevelProgress(totalXp: Long): LevelProgress
    public suspend fun checkLevelUps(userId: String, oldXp: Long, newXp: Long): List<LevelDefinition>
}
