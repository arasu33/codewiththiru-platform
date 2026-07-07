package com.codewiththiru.platform.developer.gametesting.generator

import com.codewiththiru.platform.game.profile.api.PlayerProfile
import com.codewiththiru.platform.game.profile.customization.PlayerCustomization
import com.codewiththiru.platform.game.profile.identity.PlayerIdentity
import com.codewiththiru.platform.game.profile.level.PlayerLevel
import java.util.UUID

object RandomProfileGenerator {
    fun generate(
        id: String = UUID.randomUUID().toString(),
        level: Int = (1..100).random(),
        xp: Long = (0..10000L).random(),
    ): PlayerProfile =
        PlayerProfile(
            id = id,
            displayName = "Guest-$id",
            identity = PlayerIdentity.Guest(id),
            level = PlayerLevel(currentLevel = level, currentXp = xp),
            customization = PlayerCustomization(),
        )
}
