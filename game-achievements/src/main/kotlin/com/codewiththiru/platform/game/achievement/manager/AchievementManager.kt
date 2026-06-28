package com.codewiththiru.platform.game.achievement.manager

import com.codewiththiru.platform.game.achievement.api.AchievementDefinition
import com.codewiththiru.platform.game.achievement.api.AchievementProgress
import kotlinx.coroutines.flow.StateFlow

/**
 * The primary API for consumers to query, observe, and manage achievements.
 */
interface AchievementManager {
    /**
     * Gets all registered achievement definitions.
     */
    fun getDefinitions(): List<AchievementDefinition>

    /**
     * Retrieves the current progress for a specific achievement.
     */
    suspend fun getProgress(achievementId: String): AchievementProgress?

    /**
     * Returns a reactive flow of all achievement progresses for the UI to observe.
     */
    fun observeAllProgress(): StateFlow<List<AchievementProgress>>

    /**
     * Returns a reactive flow of a specific achievement's progress.
     */
    fun observeProgress(achievementId: String): StateFlow<AchievementProgress?>

    /**
     * Allows a player to manually claim a reward for an unlocked achievement
     * (if its UnlockStrategy requires manual claiming).
     */
    suspend fun claimReward(achievementId: String): Boolean
}
