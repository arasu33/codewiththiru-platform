package com.codewiththiru.platform.growth.activation

interface ActivationManager {
    suspend fun trackEvent(event: ActivationEvent)
    suspend fun getScore(userId: String): ActivationScore
    suspend fun checkMilestones(userId: String): List<ActivationMilestone>
}

class DefaultActivationManager : ActivationManager {
    private var currentScore = 0

    override suspend fun trackEvent(event: ActivationEvent) {
        currentScore += event.weight
    }

    override suspend fun getScore(userId: String): ActivationScore {
        val level = when {
            currentScore > 100 -> ActivationLevel.POWER_USER
            currentScore > 50 -> ActivationLevel.ACTIVE
            currentScore > 10 -> ActivationLevel.WARM
            else -> ActivationLevel.NEW
        }
        return ActivationScore(userId, currentScore, level)
    }

    override suspend fun checkMilestones(userId: String): List<ActivationMilestone> {
        return listOf(
            ActivationMilestone("first_action", 10, currentScore >= 10),
            ActivationMilestone("habit_formed", 50, currentScore >= 50)
        )
    }
}
