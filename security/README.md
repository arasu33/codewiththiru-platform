# 🔒 Security Module (`:security`)

Hardware-backed security utilities, encrypted key-value storage, and device integrity verification for consumer applications.

## Features
- **SecureStorage**: Hardware-backed AES-256 GCM encrypted storage wrapping `EncryptedSharedPreferences` and Android Keystore `MasterKey`.
- **SecurityUtils**: Detection of rooted environments, emulators, attached debuggers, and ADB debugging.
- **BiometricSecurityHelper**: Check device lock status and keyguard security state.

## Quick Start
```kotlin
// 1. Dependency
implementation("com.codewiththiru.platform:security")

// 2. Encrypted Storage
val secureStorage: SecureStorage = EncryptedSecureStorage(context)
secureStorage.putString("auth_token", "eyJhbGciOi...")
val token = secureStorage.getString("auth_token")

// 3. Root & Environment Checks
if (SecurityUtils.isDeviceRooted()) {
    // Restrict high-security financial/sensitive features
}
```
