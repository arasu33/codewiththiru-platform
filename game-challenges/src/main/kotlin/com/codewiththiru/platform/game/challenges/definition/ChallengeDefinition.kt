package com.codewiththiru.platform.game.challenges.definition

import com.codewiththiru.platform.game.challenges.api.ChallengeType
import kotlinx.serialization.Serializable

/**
 * A specific condition that must be met to progress the challenge.
 * Evaluated against incoming statistics or events.
 */
@Serializable
data class ChallengeCondition(
    val statisticKey: String,
    val targetValue: Long,
    // If false, must be achieved in a single game/session
    val isCumulative: Boolean = true,
)

/**
 * The immutable blueprint of a challenge.
 */
@Serializable
data class ChallengeDefinition(
    val id: String,
    val type: ChallengeType,
    val conditions: List<ChallengeCondition>,
    // Links to :game-rewards definitions
    val rewardIds: List<String>,
    val cooldownMs: Long = 0L,
    val isRepeatable: Boolean = false,
)
