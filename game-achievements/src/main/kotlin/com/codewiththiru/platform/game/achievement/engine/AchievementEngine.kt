package com.codewiththiru.platform.game.achievement.engine

import com.codewiththiru.platform.game.achievement.api.AchievementProgress
import com.codewiththiru.platform.game.achievement.conditions.AchievementCondition
import com.codewiththiru.platform.game.statistics.api.StatisticsProfile

/**
 * Evaluates achievement conditions against current player statistics.
 */
interface AchievementEngine {
    /**
     * Registers an achievement with its unlock condition.
     */
    fun registerAchievement(
        achievementId: String,
        condition: AchievementCondition,
    )

    /**
     * Evaluates all registered achievements against the provided statistics profile.
     * Returns a list of newly updated progresses.
     */
    fun evaluateAll(
        profile: StatisticsProfile,
        currentProgresses: List<AchievementProgress>,
    ): List<AchievementProgress>
}
