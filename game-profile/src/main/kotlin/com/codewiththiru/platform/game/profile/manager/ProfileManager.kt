package com.codewiththiru.platform.game.profile.manager

import com.codewiththiru.platform.game.profile.api.PlayerProfile
import com.codewiththiru.platform.game.profile.identity.PlayerIdentity
import kotlinx.coroutines.flow.StateFlow

/**
 * Primary orchestrator for interacting with the active user's profile.
 */
interface ProfileManager {
    /**
     * Exposes the reactive state of the current user profile.
     * Null if no profile is loaded yet.
     */
    val currentProfile: StateFlow<PlayerProfile?>

    /**
     * Initializes or loads the profile for the given identity.
     */
    suspend fun login(identity: PlayerIdentity)

    /**
     * Injects raw XP into the user's progress. Triggers leveling math implicitly.
     */
    suspend fun addExperience(
        amount: Long,
        source: String,
    )

    /**
     * Updates a specific preference flag.
     */
    suspend fun updatePreference(
        key: String,
        value: Boolean,
    )

    /**
     * Unlocks and equips a cosmetic item.
     */
    suspend fun unlockCosmetic(
        itemId: String,
        type: String,
    )
}
