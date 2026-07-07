package com.codewiththiru.platform.game.profile.api

/**
 * Events for Analytics tracking.
 */
sealed class ProfileEvent {
    data class ProfileCreated(
        val profileId: String,
    ) : ProfileEvent()

    data class ProfileUpdated(
        val profileId: String,
    ) : ProfileEvent()

    data class AvatarChanged(
        val avatarId: String,
    ) : ProfileEvent()

    data class LevelUp(
        val newLevel: Int,
    ) : ProfileEvent()

    data class XPGranted(
        val amount: Long,
        val source: String,
    ) : ProfileEvent()

    data class BadgeUnlocked(
        val badgeId: String,
    ) : ProfileEvent()

    data class TitleUnlocked(
        val titleId: String,
    ) : ProfileEvent()

    data class PreferenceChanged(
        val key: String,
        val value: Boolean,
    ) : ProfileEvent()
}
