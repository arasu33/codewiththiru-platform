package com.codewiththiru.platform.game.challenges.api

import kotlinx.serialization.Serializable

/**
 * High-level categorization of the challenge duration/type.
 */
@Serializable
enum class ChallengeType {
    TUTORIAL,
    DAILY,
    WEEKLY,
    MONTHLY,
    SEASONAL,
    EVENT,
    LIMITED_TIME,
    PROGRESSIVE,
    MILESTONE,
    ENDLESS,
    CUSTOM,
}

/**
 * Tracks the lifecycle state of a specific challenge instance for a user.
 */
@Serializable
enum class ChallengeStatus {
    LOCKED,
    ACTIVE,
    COMPLETED,
    CLAIMED,
    EXPIRED,
    FAILED,
}

/**
 * Events for Analytics tracking.
 */
sealed class ChallengeEvent {
    data class ChallengeStarted(
        val challengeId: String,
        val type: ChallengeType,
    ) : ChallengeEvent()

    data class ChallengeCompleted(
        val challengeId: String,
        val type: ChallengeType,
    ) : ChallengeEvent()

    data class ChallengeFailed(
        val challengeId: String,
        val type: ChallengeType,
    ) : ChallengeEvent()

    data class ChallengeExpired(
        val challengeId: String,
    ) : ChallengeEvent()

    data class RewardClaimed(
        val challengeId: String,
        val rewardId: String,
    ) : ChallengeEvent()

    data class SeasonStarted(
        val seasonId: String,
    ) : ChallengeEvent()

    data class SeasonEnded(
        val seasonId: String,
    ) : ChallengeEvent()
}
