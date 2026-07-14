package com.codewiththiru.platform.game.settings

import kotlinx.serialization.Serializable

@Deprecated("Use SettingDefinition and SettingValue from :game-settings module instead")
@Serializable
data class GameSettings(
    val isSoundEnabled: Boolean = true,
    val isMusicEnabled: Boolean = true,
    val isHapticsEnabled: Boolean = true,
    val isNotificationsEnabled: Boolean = true,
)

@Deprecated("Use SettingsManager from :game-settings module instead")
interface GameSettingsManager {
    fun getSettings(): GameSettings

    fun updateSettings(settings: GameSettings)
}

class DefaultGameSettingsManager(
    private val saveStorage: com.codewiththiru.platform.game.save.GameStorage,
    private val serializer: com.codewiththiru.platform.game.save.StateSerializer,
) : GameSettingsManager {
    private val settingsKey = "game_general_settings"

    override fun getSettings(): GameSettings {
        val serialized = saveStorage.getString(settingsKey) ?: return GameSettings()
        return try {
            serializer.deserialize(serialized, kotlin.reflect.typeOf<GameSettings>())
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e
        } catch (e: Exception) {
            GameSettings()
        }
    }

    override fun updateSettings(settings: GameSettings) {
        saveStorage.putString(settingsKey, serializer.serialize(settings, kotlin.reflect.typeOf<GameSettings>()))
    }
}
