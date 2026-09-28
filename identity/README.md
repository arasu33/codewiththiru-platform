# 🔐 Identity & Authentication Platform (`:identity`)

Enterprise-grade, provider-agnostic Identity & Authentication SDK for Android applications.

---

## 🚀 Features

- **Multi-Method Auth**: Email/password, magic links, biometric unlock, and OAuth social login.
- **Session Management**: Multi-device token management, automatic refresh token rotation, and remote logout.
- **Token Platform**: Secure storage of JWT tokens backed by EncryptedDataStore and Android Keystore.
- **Privacy & Compliance**: GDPR-compliant data export, consent tracking, and account deletion endpoints.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:identity")
}
```

---

## ⚠️ Consumer Prerequisites

Add network and optional biometric permissions to `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Optional: If using Biometric Authentication -->
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
```
