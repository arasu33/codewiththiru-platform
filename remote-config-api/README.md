# ⚙️ Remote Config API Module (`:remote-config-api`)

Public interfaces, typed parameter keys, and evaluation contracts for the Remote Config framework (`:remote-config`).

---

## 🎯 Purpose

Pure contract module with zero dependencies on Firebase Remote Config SDK. Feature modules use `:remote-config-api` to read configuration keys (`RemoteConfigKey<T>`), allowing instant in-memory overrides and zero-boilerplate testing.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:remote-config-api")
}
```
