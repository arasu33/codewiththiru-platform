package com.codewiththiru.platform.game.reward.inventory

import com.codewiththiru.platform.game.reward.api.RewardDefinition

/**
 * Handles logic for non-countable or unique rewards (Cosmetics, Badges, Titles).
 */
interface RewardInventory {
    /**
     * Adds an item to the inventory.
     */
    suspend fun addItem(item: RewardDefinition)

    /**
     * Checks if the player owns a specific item.
     */
    suspend fun hasItem(itemId: String): Boolean

    /**
     * Uses a consumable item (e.g. Hint, Booster).
     * Returns true if successful.
     */
    suspend fun consumeItem(itemId: String): Boolean
}
