package com.codewiththiru.platform.game.challenges.manager

import com.codewiththiru.platform.game.challenges.definition.ChallengeDefinition
import com.codewiththiru.platform.game.challenges.progress.ChallengeProgress
import kotlinx.coroutines.flow.StateFlow

/**
 * Top level orchestrator for the Challenge framework.
 */
interface ChallengeManager {
    /**
     * Reactive state of all currently active challenges.
     */
    val activeChallenges: StateFlow<List<ChallengeProgress>>

    /**
     * Retrieves the underlying definition for a specific challenge ID.
     */
    fun getDefinition(challengeId: String): ChallengeDefinition?

    /**
     * Ingests a raw statistic update (e.g. "games_played" + 1).
     * The engine will evaluate this against all active challenges.
     */
    suspend fun trackStatistic(
        statisticKey: String,
        delta: Long,
    )

    /**
     * Manually claims the rewards for a completed challenge.
     * Integrates internally with :game-rewards RewardManager.
     */
    suspend fun claimReward(challengeId: String): Boolean

    /**
     * Triggers a manual rotation check (useful on app resume to check if dailies expired).
     */
    suspend fun checkRotations(currentTimeMs: Long)
}
