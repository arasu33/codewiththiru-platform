# ⚙️ Settings Module (`:settings`)

Modular, persistent application settings and preferences screen built with Jetpack Compose.

## Features
- **Theme Switching**: System default, Light, and Dark modes.
- **Dynamic Color**: Material You dynamic wallpaper color toggling (Android 12+).
- **Preferences Management**: Push notifications, analytics consent, and diagnostic toggles.
- **DataStore Persistence**: `DataStoreSettingsRepository` provides reactive `Flow<AppSettings>`.
- **Pre-built UI**: `SettingsScreen` with Material 3 styling and divider sections.

## Quick Start
```kotlin
// 1. Dependency
implementation("com.codewiththiru.platform:settings")

// 2. Render Screen
SettingsScreen(
    settings = appSettings,
    versionName = "1.5.0",
    onThemeSelected = { repository.setTheme(it) },
    onDynamicColorChanged = { repository.setDynamicColor(it) },
    onNotificationsChanged = { repository.setNotificationsEnabled(it) },
    onAnalyticsChanged = { repository.setAnalyticsEnabled(it) },
    onAboutClicked = { navController.navigate("about") }
)
```
