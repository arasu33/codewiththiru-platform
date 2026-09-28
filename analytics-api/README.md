# 📊 Analytics API Module (`:analytics-api`)

Public interfaces, event contracts, and schema definitions for the Analytics framework (`:analytics`).

---

## 🎯 Purpose

Pure contract module with zero external dependencies (no Firebase, Play Services, or network libraries). Feature modules should depend on `:analytics-api` to log events, allowing implementations to be swapped or mocked in unit tests without bringing in Firebase SDKs.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:analytics-api")
}
```
