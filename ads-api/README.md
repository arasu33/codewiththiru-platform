# 🎯 Ads API Module (`:ads-api`)

Public interfaces and contract models for the Ads framework (`:ads`).

---

## 🎯 Purpose

This module contains **zero implementation dependencies** (no AdMob, Play Services, or heavyweight SDKs). Feature modules should depend on `:ads-api` rather than the full `:ads` implementation, enabling clean dependency inversion and effortless test mocking.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:ads-api")
}
```
