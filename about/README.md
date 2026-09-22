# ℹ️ About Module

Configuration-driven Jetpack Compose module providing an "About this App" screen.

## Features
- **App Metadata**: Displays app name, package name, version, and icon.
- **Developer Info**: Social links and developer profile.
- **Device Diagnostics**: OS version, API level, manufacturer, model.
- **Legal Links**: Privacy Policy, Terms of Service, Open-Source Licenses.
- **Diagnostics Payload**: Copy/preview support for support tickets.

## Quick Start
1. Add dependency:
```kotlin
implementation("com.codewiththiru.platform:about")
```

2. Configure and render:
```kotlin
val config = AboutConfig.Builder()
    .setAppInfo(
        appName = "StudySnap",
        packageName = "com.studysnap.app",
        versionName = "1.4.0",
        versionCode = 24
    )
    .build()

AboutScreen(
    uiState = AboutUiState.Success(config),
    eventListener = myEventListener
)
```

## Consumer Prerequisites
- **Zero-config**: Does not require any external SDKs, Firebase, or manifest permissions.
