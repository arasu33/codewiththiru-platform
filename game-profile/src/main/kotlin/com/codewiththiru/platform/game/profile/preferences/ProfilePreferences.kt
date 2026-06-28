package com.codewiththiru.platform.game.profile.preferences

import kotlinx.serialization.Serializable

/**
 * Standard user toggle preferences.
 */
@Serializable
data class ProfilePreferences(
    val isAudioEnabled: Boolean = true,
    val isHapticsEnabled: Boolean = true,
    val isPushNotificationsEnabled: Boolean = false,
    val languageCode: String = "en",
    val accessibilityReducedMotion: Boolean = false,
)
