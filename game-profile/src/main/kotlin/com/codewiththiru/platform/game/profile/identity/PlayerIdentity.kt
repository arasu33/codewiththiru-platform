package com.codewiththiru.platform.game.profile.identity

import kotlinx.serialization.Serializable

/**
 * Represents the authentication and ownership layer of the profile.
 */
@Serializable
sealed class PlayerIdentity {
    /** A local-only profile with no cloud backup. */
    @Serializable
    data class Local(
        val deviceId: String,
    ) : PlayerIdentity()

    /** A temporary profile for a user who hasn't committed to playing. */
    @Serializable
    data class Guest(
        val sessionId: String,
    ) : PlayerIdentity()

    /** A fully authenticated cloud profile (e.g., Google Play Games, Firebase). */
    @Serializable
    data class Cloud(
        val providerId: String,
        val userId: String,
    ) : PlayerIdentity()
}
