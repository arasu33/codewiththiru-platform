package com.codewiththiru.platform.game.challenges.progress

import com.codewiththiru.platform.game.challenges.api.ChallengeStatus
import kotlinx.serialization.Serializable

/**
 * Tracks the live state of an active challenge for a user.
 */
@Serializable
data class ChallengeProgress(
    val challengeId: String,
    val status: ChallengeStatus,
    // Mapping of Condition statisticKey to current progress
    val currentValues: Map<String, Long>,
    val activationTimeMs: Long,
    val expirationTimeMs: Long? = null,
) {
    /**
     * Helper to check if a specific condition is met based on a target value.
     */
    fun isConditionMet(
        statisticKey: String,
        target: Long,
    ): Boolean = (currentValues[statisticKey] ?: 0L) >= target
}
