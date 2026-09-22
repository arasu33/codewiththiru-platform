# 📊 Analytics Module

Production-grade, offline-first Android analytics engine with Firebase Analytics backend.

## ⚠️ Consumer Prerequisites

Before using this module, your consumer app **must** complete the following setup:

### 1. Firebase Project Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a project (or use existing)
3. Add your Android app (use your app's package name)
4. Download `google-services.json`
5. Place it in your **app/** module root directory

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
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Optional: Android 13+ advertising ID collection -->
<!-- <uses-permission android:name="com.google.android.gms.permission.AD_ID" /> -->
```

### 4. BOM Dependency
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:<version>"))
    implementation("com.codewiththiru.platform:analytics")
}
```

## Features
- **Offline-first**: Events queued in DataStore, batched and flushed periodically
- **PII scrubbing**: Automatic redaction of emails, phone numbers, and sensitive data
- **Privacy & consent**: Built-in consent management (GDPR/CCPA compliant)
- **Multi-provider**: Fan-out to Firebase Analytics + Logcat (debug) simultaneously
- **Dead-letter queue**: Failed events are preserved for retry
- **Export**: BigQuery map structure and CSV export support

## Architecture
```
analytics-api (interfaces) ← analytics (implementation)
                                  ↓
                          FirebaseAnalyticsProvider
                          LogcatAnalyticsProvider
                          NoOpAnalyticsProvider
```

## Common Pitfalls
- ❌ Missing `google-services.json` → `IllegalStateException` at runtime
- ❌ Forgetting Google Services plugin → Firebase won't initialize
- ❌ Testing on emulator without Play Services → Firebase may not work
- ✅ Use `LogcatAnalyticsProvider` for local development/testing
