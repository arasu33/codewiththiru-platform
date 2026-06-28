package com.codewiththiru.platform.game.settings.testing

import com.codewiththiru.platform.game.settings.api.SettingKey
import com.codewiththiru.platform.game.settings.api.SettingValue
import com.codewiththiru.platform.game.settings.migration.SettingMigration

class TestSettingMigration : SettingMigration {
    override fun migrate(
        oldKey: SettingKey,
        oldValue: SettingValue,
    ): Pair<SettingKey, SettingValue>? {
        // Example: "music_volume" (int 0-100) -> "is_music_enabled" (boolean > 0)
        if (oldKey.value == "music_volume" && oldValue is SettingValue.IntValue) {
            return Pair(
                SettingKey("is_music_enabled"),
                SettingValue.BooleanValue(oldValue.value > 0),
            )
        }
        return Pair(oldKey, oldValue)
    }
}
