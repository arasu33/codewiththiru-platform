package com.codewiththiru.platform.game.reward.claims

import com.codewiththiru.platform.game.reward.api.RewardDefinition

/**
 * Handles the logic of granting rewards, preventing duplicates and managing claims.
 */
interface ClaimEngine {
    /**
     * Attempts to claim a reward.
     * Returns true if successful. If false, it might be due to duplicate protection,
     * idempotency keys, or wallet caps.
     */
    suspend fun claim(
        reward: RewardDefinition,
        idempotencyKey: String,
    ): Boolean

    /**
     * Defers a claim to be collected manually by the player later.
     */
    suspend fun deferClaim(reward: RewardDefinition)

    /**
     * Gets all pending deferred claims for the player.
     */
    suspend fun getPendingClaims(): List<RewardDefinition>
}
