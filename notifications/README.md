# 🔔 Notifications Module

Push notification engine powered by Firebase Cloud Messaging (FCM) with rich notification support, topic management, and WorkManager-based scheduling.

## ⚠️ Consumer Prerequisites

Before using this module, your consumer app **must** complete the following setup:

### 1. Firebase Project Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Enable **Cloud Messaging** for your project
3. Download `google-services.json` (with FCM enabled)
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

<!-- Required for Android 13+ (API 33) — must also request at runtime -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### 4. Notification Channel & Icon (Recommended)
Add default notification metadata to your `AndroidManifest.xml` inside `<application>`:
```xml
<!-- Default notification channel -->
<meta-data
    android:name="com.google.firebase.messaging.default_notification_channel_id"
    android:value="@string/default_notification_channel_id" />

<!-- Default notification icon (must be white-on-transparent) -->
<meta-data
    android:name="com.google.firebase.messaging.default_notification_icon"
    android:resource="@drawable/ic_notification" />

<!-- Default notification color -->
<meta-data
    android:name="com.google.firebase.messaging.default_notification_color"
    android:resource="@color/notification_accent" />
```

### 5. Runtime Permission (Android 13+)
```kotlin
// In your Activity/Fragment
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
        REQUEST_CODE_NOTIFICATIONS
    )
}
```

### 6. BOM Dependency
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:notifications")
}
```

## Features
- **FCM integration**: Token management, topic subscriptions
- **Rich notifications**: Images, actions, deep links
- **Scheduling**: WorkManager-based scheduled/recurring notifications
- **Analytics**: Automatic notification open/dismiss tracking
- **Compose UI**: In-app notification banners and permission request dialogs

## Common Pitfalls
- ❌ Missing `google-services.json` → FCM token retrieval fails silently
- ❌ No notification channel on Android 8+ → Notifications won't display
- ❌ Missing POST_NOTIFICATIONS permission on Android 13+ → Notifications blocked
- ❌ White notification icon must be monochrome → Colored icons appear as white square
- ✅ Create notification channels in Application.onCreate()
