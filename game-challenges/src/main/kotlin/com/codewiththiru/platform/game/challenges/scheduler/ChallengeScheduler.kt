package com.codewiththiru.platform.game.challenges.scheduler

import com.codewiththiru.platform.game.challenges.api.ChallengeType

/**
 * Handles time-based rotation (Daily/Weekly resets), grace periods, and cooldown math.
 */
interface ChallengeScheduler {
    /**
     * Determines the exact timestamp when a challenge of a specific type should expire/rotate.
     */
    fun calculateExpirationTime(
        type: ChallengeType,
        activationTimeMs: Long,
    ): Long

    /**
     * Returns true if the challenge is currently in a "grace period" (e.g. allowing late completion).
     */
    fun isInGracePeriod(
        expirationTimeMs: Long,
        currentTimeMs: Long,
    ): Boolean

    /**
     * Checks if a user is still under cooldown for a repeatable challenge.
     */
    fun isUnderCooldown(
        lastCompletionTimeMs: Long,
        cooldownDurationMs: Long,
        currentTimeMs: Long,
    ): Boolean
}
