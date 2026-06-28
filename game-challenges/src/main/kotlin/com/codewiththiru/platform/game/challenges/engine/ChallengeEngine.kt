package com.codewiththiru.platform.game.challenges.engine

import com.codewiththiru.platform.game.challenges.definition.ChallengeDefinition
import com.codewiththiru.platform.game.challenges.progress.ChallengeProgress

/**
 * The core processing loop that evaluates conditions against live statistics.
 */
interface ChallengeEngine {
    /**
     * Processes an incoming statistic update and applies it to an active challenge.
     * Returns the mutated ChallengeProgress.
     */
    fun processStatistic(
        definition: ChallengeDefinition,
        currentProgress: ChallengeProgress,
        statisticKey: String,
        delta: Long,
    ): ChallengeProgress

    /**
     * Fully evaluates if a challenge's progress has met all definition conditions.
     */
    fun evaluateCompletion(
        definition: ChallengeDefinition,
        progress: ChallengeProgress,
    ): Boolean
}
