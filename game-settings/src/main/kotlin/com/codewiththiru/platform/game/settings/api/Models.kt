package com.codewiththiru.platform.game.settings.api

import kotlinx.serialization.Serializable

/**
 * Strongly typed wrapper for setting keys to avoid stringly-typed errors.
 */
@Serializable
@JvmInline
value class SettingKey(
    val value: String,
)

/**
 * A sealed hierarchy representing all supported generic primitive and complex setting types.
 */
@Serializable
sealed class SettingValue {
    @Serializable data class BooleanValue(
        val value: Boolean,
    ) : SettingValue()

    @Serializable data class IntValue(
        val value: Int,
    ) : SettingValue()

    @Serializable data class LongValue(
        val value: Long,
    ) : SettingValue()

    @Serializable data class FloatValue(
        val value: Float,
    ) : SettingValue()

    @Serializable data class DoubleValue(
        val value: Double,
    ) : SettingValue()

    @Serializable data class StringValue(
        val value: String,
    ) : SettingValue()

    // For Enums, Themes, Colors etc we can serialize them to Strings or JSON generically
    @Serializable data class JsonValue(
        val jsonString: String,
    ) : SettingValue()
}

/**
 * The immutable definition of what a setting represents, its default value, and its constraints.
 */
data class SettingDefinition(
    val key: SettingKey,
    val group: String,
    val defaultValue: SettingValue,
    val isExportable: Boolean = true,
)

/**
 * Analytics events
 */
sealed class SettingsEvent {
    data class SettingChanged(
        val key: SettingKey,
        val oldValue: SettingValue?,
        val newValue: SettingValue,
    ) : SettingsEvent()

    data class SettingsReset(
        val group: String?,
    ) : SettingsEvent()

    data class SettingsImported(
        val count: Int,
    ) : SettingsEvent()

    data class SettingsExported(
        val count: Int,
    ) : SettingsEvent()
}
