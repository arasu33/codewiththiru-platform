# 🛡️ Enterprise Security Platform (`:security-platform`)

Enterprise-grade cryptography, Android Keystore management, root/tamper detection, and TLS certificate pinning.

---

## 🚀 Capabilities

- **Android Keystore Encryption**: Hardware-backed AES-256-GCM encryption with configurable master key aliases (`AndroidKeystoreEncryptionProvider(keyAlias = "my_custom_key")`).
- **Integrity & Tamper Monitoring**: Detect rooted environments, hooked processes (Frida/Xposed), emulators, and tampered APK signatures.
- **Network Security**: OkHttp network interceptors enforcing strict TLS 1.3 and certificate pinning.
- **Session & Fraud Defense**: Detect rapid request anomalies, injected scripts, and man-in-the-middle attacks.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:security-platform")
}
```
