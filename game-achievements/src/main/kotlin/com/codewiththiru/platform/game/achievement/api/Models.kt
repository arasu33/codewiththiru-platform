package com.codewiththiru.platform.game.achievement.api

import kotlinx.serialization.Serializable

/**
 * Types of achievements supported.
 */
enum class AchievementType {
    STANDARD,
    HIDDEN,
    SECRET,
    PROGRESSIVE,
    MILESTONE,
    COLLECTION,
    TIME_BASED,
    EVENT_BASED,
}

/**
 * Status of an achievement's progress.
 */
enum class AchievementStatus {
    LOCKED,
    IN_PROGRESS,
    UNLOCKED,
    CLAIMED, // Used if manual unlock strategy is applied
}

/**
 * Categories to group achievements (e.g. "Combat", "Exploration").
 */
@Serializable
data class AchievementCategory(
    val id: String,
    val name: String,
    val description: String = "",
)

/**
 * Definition of what the achievement is and its metadata.
 */
@Serializable
data class AchievementDefinition(
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String,
    val type: AchievementType = AchievementType.STANDARD,
    val maxProgress: Double = 1.0,
    val isPremium: Boolean = false,
)

/**
 * The current state/progress of an achievement for a player.
 */
@Serializable
data class AchievementProgress(
    val achievementId: String,
    val currentProgress: Double = 0.0,
    val status: AchievementStatus = AchievementStatus.LOCKED,
    val unlockTimeMs: Long? = null,
)
