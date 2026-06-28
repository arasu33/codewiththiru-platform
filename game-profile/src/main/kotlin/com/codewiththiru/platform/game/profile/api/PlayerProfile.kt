package com.codewiththiru.platform.game.profile.api

import com.codewiththiru.platform.game.profile.customization.PlayerCustomization
import com.codewiththiru.platform.game.profile.identity.PlayerIdentity
import com.codewiththiru.platform.game.profile.level.PlayerLevel
import com.codewiththiru.platform.game.profile.preferences.ProfilePreferences
import kotlinx.serialization.Serializable

/**
 * The root aggregate defining a user's existence in the game.
 */
@Serializable
data class PlayerProfile(
    val id: String,
    val displayName: String,
    val identity: PlayerIdentity,
    val level: PlayerLevel = PlayerLevel(),
    val customization: PlayerCustomization = PlayerCustomization(),
    val preferences: ProfilePreferences = ProfilePreferences(),
    val creationTimeMs: Long = System.currentTimeMillis(),
)
