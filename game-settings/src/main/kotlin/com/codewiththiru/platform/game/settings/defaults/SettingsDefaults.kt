package com.codewiththiru.platform.game.settings.defaults

import com.codewiththiru.platform.game.settings.api.SettingDefinition
import com.codewiththiru.platform.game.settings.api.SettingValue

/**
 * Resolves the ultimate default value of a setting, checking remote config overrides first.
 */
interface SettingsDefaults {
    /**
     * Returns the factory default, unless remote config has overridden it via an experiment/feature flag.
     */
    fun getDefaultValue(definition: SettingDefinition): SettingValue
}
