# ℹ️ About Module (`:about`)

Configuration-driven Jetpack Compose module providing an "About this App" screen with full customization and turnkey defaults.

---

## 🚀 Features

- **App Metadata**: Displays app name, package name, version, and icon.
- **Developer Info**: Social links and developer profile (optional).
- **Device Diagnostics**: OS version, API level, manufacturer, model.
- **Legal Links**: Privacy Policy, Terms of Service, Open-Source Licenses.
- **Diagnostics Payload**: Copy/preview support for support tickets.
- **Turnkey Default Screen**: One-liner `DefaultAboutScreen` with sensible fallbacks.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:about")
}
```

---

## 💡 Quick Start

### Approach 1: Turnkey Default Screen (Recommended)
```kotlin
val config = AboutDefaults.defaultConfig(
    appName = "My App",
    packageName = "com.example.myapp",
    versionName = "1.5.0",
    developerName = "Acme Corp",
    developerEmail = "support@example.com",
    privacyPolicyUrl = "https://example.com/privacy",
    termsOfServiceUrl = "https://example.com/terms"
)

DefaultAboutScreen(config = config)
```

### Approach 2: Granular Builder
```kotlin
val config = AboutConfig.Builder()
    .setAppInfo(
        AppInfo(
            appName = "My App",
            packageName = "com.example.myapp",
            versionName = "1.5.0",
            versionCode = 1,
            buildType = "release"
        )
    )
    .build()

AboutScreen(
    uiState = AboutUiState.Success(config),
    eventListener = myEventListener
)
```

---

## ⚡ Zero Configuration
Does not require any external SDKs, Firebase setup, or manifest permissions.
