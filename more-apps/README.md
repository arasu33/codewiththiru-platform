# 📱 More Apps Module (`:more-apps`)

Plug-and-play Jetpack Compose cross-promotion module to showcase partner and portfolio applications with dynamic remote catalog feeds.

---

## ⚠️ Consumer Prerequisites

### 1. Package Visibility Queries (Android 11+ / API 30+)
To allow the app to detect if featured apps are already installed, declare `<queries>` in your app's `AndroidManifest.xml`:

```xml
<queries>
    <!-- Add package names of apps you cross-promote -->
    <package android:name="com.example.app1" />
    <package android:name="com.example.app2" />
    
    <!-- Or query Play Store directly -->
    <intent>
        <action android:name="android.intent.action.VIEW" />
        <data android:scheme="market" />
    </intent>
</queries>
```

### 2. Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 3. BOM Dependency (`build.gradle.kts`)
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:more-apps")
}
```

---

## 🚀 Features

- **Configurable Layouts**: Grid, List, and Horizontal Carousel presentations.
- **Install Status Detection**: Dynamically displays "Open" vs "Install" buttons based on local package presence.
- **Image Loader Agnostic**: Uses `MoreAppsImageProvider` so you can plug in Coil, Glide, or Compose native painters.

---

## 📚 Documentation
See [API_GUIDE.md](API_GUIDE.md) for full usage examples.
