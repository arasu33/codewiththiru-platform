# 🤖 Core Android Module (`:core-android`)

Android-specific foundational primitives, AndroidX App Startup initializers, and context extensions for the CodeWithThiru platform.

---

## 🚀 Features

- **AndroidX App Startup (`PlatformInitializer`)**: Automatically bootstraps required platform core services upon application process initialization without needing boilerplate code in your custom `Application` class.
- **Context & System Utilities**: Safe helpers for querying connectivity, device metrics, display insets, and system managers.
- **Coroutines Lifecycle Scopes**: Seamlessly bridge Kotlin coroutines and Android component lifecycles.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:core-android")
}
```

---

## ⚡ Zero Configuration
The module automatically merges its startup initializer into your app's merged manifest via `androidx.startup.InitializationProvider`. No manual `init(context)` call is required in `Application.onCreate()`.
