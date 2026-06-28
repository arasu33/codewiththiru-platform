package com.codewiththiru.platform.game.achievement.conditions

import com.codewiththiru.platform.game.statistics.api.StatisticsMetric
import com.codewiththiru.platform.game.statistics.api.StatisticsProfile

/**
 * A condition that must be met to unlock an achievement.
 */
interface AchievementCondition {
    /**
     * Evaluates whether the condition is met based on the player's statistics.
     * Returns a pair of (isMet: Boolean, currentProgress: Double)
     */
    fun evaluate(profile: StatisticsProfile): Pair<Boolean, Double>
}

/**
 * Evaluates a condition based on a specific statistic metric.
 */
data class StatisticCondition(
    val metric: StatisticsMetric,
    val targetValue: Double,
    val isGreaterThan: Boolean = true,
) : AchievementCondition {
    override fun evaluate(profile: StatisticsProfile): Pair<Boolean, Double> {
        val currentValue = profile.metrics[metric] ?: 0.0
        val isMet = if (isGreaterThan) currentValue >= targetValue else currentValue <= targetValue
        return Pair(isMet, currentValue)
    }
}

/**
 * Combines multiple conditions using logical AND or OR.
 */
data class CompositeCondition(
    val conditions: List<AchievementCondition>,
    val requireAll: Boolean = true,
) : AchievementCondition {
    override fun evaluate(profile: StatisticsProfile): Pair<Boolean, Double> {
        if (conditions.isEmpty()) return Pair(true, 1.0)

        var metCount = 0
        var totalProgress = 0.0

        for (condition in conditions) {
            val (isMet, progress) = condition.evaluate(profile)
            if (isMet) metCount++
            totalProgress += progress
        }

        val isMet = if (requireAll) metCount == conditions.size else metCount > 0
        // Rough average progress across conditions
        val avgProgress = totalProgress / conditions.size
        return Pair(isMet, avgProgress)
    }
}
