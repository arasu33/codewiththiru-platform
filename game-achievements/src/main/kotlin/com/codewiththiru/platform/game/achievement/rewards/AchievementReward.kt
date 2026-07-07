package com.codewiththiru.platform.game.achievement.rewards

/**
 * Represents a reward granted upon unlocking an achievement.
 * Designed to integrate with a future :game-rewards module.
 */
sealed interface AchievementReward {
    val id: String
    val amount: Int

    data class Coins(
        override val id: String = "reward_coins",
        override val amount: Int,
    ) : AchievementReward

    data class XP(
        override val id: String = "reward_xp",
        override val amount: Int,
    ) : AchievementReward

    data class Stars(
        override val id: String = "reward_stars",
        override val amount: Int,
    ) : AchievementReward

    // Future integrations for cosmetics, titles, badges
    data class Custom(
        override val id: String,
        override val amount: Int,
        val metadata: String,
    ) : AchievementReward
}
