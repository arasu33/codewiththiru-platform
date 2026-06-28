package com.codewiththiru.platform.game.profile.customization

import kotlinx.serialization.Serializable

/**
 * Encapsulates the cosmetic inventory and current selections of the user.
 */
@Serializable
data class PlayerCustomization(
    val currentAvatarId: String? = null,
    val currentFrameId: String? = null,
    val currentTitleId: String? = null,
    val currentThemeId: String? = null,
    val unlockedAvatars: Set<String> = emptySet(),
    val unlockedFrames: Set<String> = emptySet(),
    val unlockedTitles: Set<String> = emptySet(),
    val unlockedThemes: Set<String> = emptySet(),
    // Max 3 usually
    val pinnedBadges: List<String> = emptyList(),
)
