package com.codewiththiru.platform.game.reward.api

import kotlinx.serialization.Serializable

/**
 * Types of rewards that can be granted.
 */
enum class RewardType {
    COINS,
    STARS,
    XP,
    LIVES,
    TICKETS,
    ENERGY,
    KEYS,
    BOOSTERS,
    HINTS,
    UNDO_TOKENS,
    PREMIUM_TOKENS,
    COSMETICS,
    BADGES,
    TITLES,
    AVATARS,
    FRAMES,
    THEMES,
    CUSTOM,
}

/**
 * Category grouping for rewards.
 */
@Serializable
data class RewardCategory(
    val id: String,
    val name: String,
)

/**
 * Sources where a reward originated from.
 */
enum class RewardSource {
    GAME_COMPLETION,
    PERFECT_GAME,
    DAILY_LOGIN,
    DAILY_CHALLENGE,
    WEEKLY_CHALLENGE,
    MONTHLY_CHALLENGE,
    ACHIEVEMENT_UNLOCK,
    ADVERTISEMENT,
    PURCHASE,
    PROMOTION,
    REFERRAL,
    SEASONAL_EVENT,
    TOURNAMENT,
    MANUAL_GRANT,
    UNKNOWN,
}

/**
 * Represents a specific reward grant definition.
 */
@Serializable
data class RewardDefinition(
    val id: String,
    val type: RewardType,
    val amount: Long,
    val source: RewardSource,
    val categoryId: String? = null,
    val metadata: Map<String, String> = emptyMap(),
)

/**
 * Ledger entry for audit history.
 */
@Serializable
data class RewardLedger(
    val transactionId: String,
    val timestampMs: Long,
    val definition: RewardDefinition,
    val isDeposit: Boolean,
)

/**
 * Aggregate balance representation for countable currencies.
 */
@Serializable
data class RewardBalance(
    val type: RewardType,
    val totalAmount: Long,
)
