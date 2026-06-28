package com.codewiththiru.platform.game.reward.multiplier

import com.codewiththiru.platform.game.reward.api.RewardDefinition

/**
 * Represents a modifier applied to a base reward.
 */
data class Multiplier(
    val id: String,
    val factor: Double,
    val isActive: Boolean = true,
)

/**
 * Calculates final rewards based on active multipliers.
 */
interface RewardMultiplierEngine {
    /**
     * Returns the list of currently active multipliers.
     */
    fun getActiveMultipliers(): List<Multiplier>

    /**
     * Applies active multipliers to the base reward definition.
     * Stacks them multiplicatively.
     */
    fun applyMultipliers(baseReward: RewardDefinition): RewardDefinition {
        val totalFactor =
            getActiveMultipliers()
                .filter { it.isActive }
                .fold(1.0) { acc, multiplier -> acc * multiplier.factor }

        return baseReward.copy(
            amount = (baseReward.amount * totalFactor).toLong(),
        )
    }
}
