package com.codewiththiru.settings.model

/**
 * Visual theme preference of the application.
 */
enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK,
}

/**
 * Snapshot of general application settings and user preferences.
 */
data class AppSettings(
    val theme: AppTheme = AppTheme.SYSTEM,
    val dynamicColor: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val analyticsEnabled: Boolean = true,
    val diagnosticsEnabled: Boolean = false,
)
