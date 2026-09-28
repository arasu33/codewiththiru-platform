# ⚙️ Remote Config Module

Firebase Remote Config wrapper with local caching, encrypted storage, and type-safe parameter access.

## ⚠️ Consumer Prerequisites

Before using this module, your consumer app **must** complete the following setup:

### 1. Firebase Project Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Navigate to **Remote Config** and add your parameters
3. Download `google-services.json`
4. Place it in your **app/** module root directory

### 2. Gradle Configuration
```kotlin
// Root build.gradle.kts (project level)
plugins {
    id("com.google.gms.google-services") version "4.4.2" apply false
}

// App build.gradle.kts (app module)
plugins {
    id("com.google.gms.google-services")
}
```

### 3. Permissions
Add to your app's `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 4. Default Values (Recommended)
Create `res/xml/remote_config_defaults.xml` in your app module:
```xml
<?xml version="1.0" encoding="utf-8"?>
<defaultsMap>
    <entry>
        <key>feature_new_ui_enabled</key>
        <value>false</value>
    </entry>
    <entry>
        <key>min_required_version</key>
        <value>100</value>
    </entry>
</defaultsMap>
```

### 5. BOM Dependency
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:remote-config")
}
```

## Features
- **Type-safe access**: Get parameters as String, Boolean, Long, Double, or JSON
- **Local caching**: DataStore-backed cache for offline resilience
- **Encrypted storage**: Sensitive config values stored with AndroidX Security Crypto
- **Analytics integration**: Automatic tracking of config fetch/activate events
- **Fetch throttling**: Built-in minimum fetch interval management

## Architecture
```
remote-config-api (interfaces) ← remote-config (implementation)
                                        ↓
                                FirebaseRemoteConfigProvider
                                LocalCacheProvider (DataStore)
                                EncryptedConfigStorage
```

## Common Pitfalls
- ❌ Missing `google-services.json` → Firebase initialization crash
- ❌ No default values → Parameters return empty/zero until first fetch
- ❌ Fetching too frequently → Throttled by Firebase (12-hour cache by default)
- ✅ Always set local defaults for critical feature flags
- ✅ Use `fetchAndActivate()` on app startup for fresh values
