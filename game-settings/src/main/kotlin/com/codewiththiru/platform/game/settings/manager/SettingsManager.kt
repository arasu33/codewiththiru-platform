package com.codewiththiru.platform.game.settings.manager

import com.codewiththiru.platform.game.settings.api.SettingDefinition
import com.codewiththiru.platform.game.settings.api.SettingKey
import com.codewiththiru.platform.game.settings.api.SettingValue
import com.codewiththiru.platform.game.settings.backup.SettingsSnapshot
import kotlinx.coroutines.flow.StateFlow

/**
 * Top level orchestrator for the Settings framework.
 */
interface SettingsManager {
    /**
     * Retrieves the current value of a setting, resolving defaults if not explicitly set.
     */
    fun getValue(key: SettingKey): SettingValue

    /**
     * Exposes a reactive flow for a specific setting to drive UI changes.
     */
    fun observeValue(key: SettingKey): StateFlow<SettingValue>

    /**
     * Attempts to update a setting. Returns false if validation fails.
     */
    suspend fun updateValue(
        key: SettingKey,
        value: SettingValue,
    ): Boolean

    /**
     * Resets a specific group (or all if null) back to default.
     */
    suspend fun resetToDefault(group: String? = null)

    /**
     * Registers definitions dynamically on app start.
     */
    fun registerDefinitions(definitions: List<SettingDefinition>)

    /**
     * Exports all exportable settings into a Snapshot for cloud backup.
     */
    fun exportSnapshot(): SettingsSnapshot

    /**
     * Overwrites current settings from a Snapshot (used on device restore).
     */
    suspend fun importSnapshot(snapshot: SettingsSnapshot)
}
