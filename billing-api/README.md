# 💳 Billing API Module (`:billing-api`)

Public interfaces, purchase models, and subscription contracts for the Billing framework (`:billing`).

---

## 🎯 Purpose

Pure contract module with zero dependencies on Google Play Billing Client. Feature modules can depend on `:billing-api` to query entitlements and listen to purchase flows while remaining fully decoupled from Google Play services.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:billing-api")
}
```
