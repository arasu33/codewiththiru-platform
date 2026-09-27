package com.codewiththiru.settings.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codewiththiru.settings.model.AppSettings
import com.codewiththiru.settings.model.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "cwt_app_settings")

/**
 * Interface to observe and persist application-wide preferences.
 */
interface SettingsRepository {
    /** Reactive stream of the current application settings. */
    val settings: Flow<AppSettings>

    suspend fun setTheme(theme: AppTheme)

    suspend fun setDynamicColor(enabled: Boolean)

    suspend fun setNotificationsEnabled(enabled: Boolean)

    suspend fun setAnalyticsEnabled(enabled: Boolean)

    suspend fun setDiagnosticsEnabled(enabled: Boolean)
}

/**
 * DataStore-backed persistent implementation of [SettingsRepository].
 */
class DataStoreSettingsRepository(
    private val context: Context,
) : SettingsRepository {
    private val themeKey = stringPreferencesKey("app_theme")
    private val dynamicColorKey = booleanPreferencesKey("dynamic_color")
    private val notificationsKey = booleanPreferencesKey("notifications_enabled")
    private val analyticsKey = booleanPreferencesKey("analytics_enabled")
    private val diagnosticsKey = booleanPreferencesKey("diagnostics_enabled")

    override val settings: Flow<AppSettings> =
        context.settingsDataStore.data.map { prefs ->
            val themeName = prefs[themeKey] ?: AppTheme.SYSTEM.name
            val theme =
                try {
                    AppTheme.valueOf(themeName)
                } catch (_: Exception) {
                    AppTheme.SYSTEM
                }
            AppSettings(
                theme = theme,
                dynamicColor = prefs[dynamicColorKey] ?: true,
                notificationsEnabled = prefs[notificationsKey] ?: true,
                analyticsEnabled = prefs[analyticsKey] ?: true,
                diagnosticsEnabled = prefs[diagnosticsKey] ?: false,
            )
        }

    override suspend fun setTheme(theme: AppTheme) {
        context.settingsDataStore.edit { it[themeKey] = theme.name }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        context.settingsDataStore.edit { it[dynamicColorKey] = enabled }
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[notificationsKey] = enabled }
    }

    override suspend fun setAnalyticsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[analyticsKey] = enabled }
    }

    override suspend fun setDiagnosticsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[diagnosticsKey] = enabled }
    }
}
