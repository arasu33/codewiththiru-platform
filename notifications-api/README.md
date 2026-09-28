# 🔔 Notifications API Module (`:notifications-api`)

Public interfaces, notification payloads, and scheduling contracts for the Notifications framework (`:notifications`).

---

## 🎯 Purpose

Pure contract module with zero dependencies on Firebase Cloud Messaging (FCM) or WorkManager. Allows domain and feature modules to construct notifications, dispatch payloads, and validate deep links without binding to Android platform services directly.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:notifications-api")
}
```
