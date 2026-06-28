package com.codewiththiru.platform.game.settings.migration

import com.codewiththiru.platform.game.settings.api.SettingKey
import com.codewiththiru.platform.game.settings.api.SettingValue

/**
 * Handles the migration of deprecated or changed setting keys/values across versions.
 */
interface SettingMigration {
    /**
     * @return the new Key-Value pair, or null if the setting should be deleted entirely.
     */
    fun migrate(
        oldKey: SettingKey,
        oldValue: SettingValue,
    ): Pair<SettingKey, SettingValue>?
}
