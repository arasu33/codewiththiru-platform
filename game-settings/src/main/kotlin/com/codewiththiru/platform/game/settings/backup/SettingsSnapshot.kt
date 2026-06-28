package com.codewiththiru.platform.game.settings.backup

import com.codewiththiru.platform.game.settings.api.SettingValue
import kotlinx.serialization.Serializable

/**
 * A frozen snapshot of settings used for Cloud Backups, Exporting, and Importing.
 */
@Serializable
data class SettingsSnapshot(
    val timestampMs: Long,
    // SettingKey.value to Value
    val entries: Map<String, SettingValue>,
)
