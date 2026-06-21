# Security Module

## Purpose
The `security` module provides a robust set of tools for safeguarding user data, managing encryption, and handling biometric authentication. It ensures the platform complies with enterprise security standards and protects against common vulnerabilities.

## Architecture
- **Secure Storage**: Wraps `EncryptedSharedPreferences` for storing sensitive key-value pairs (e.g., auth tokens).
- **Keystore Manager**: Manages cryptographic keys securely within the Android hardware-backed Keystore.
- **Biometric Wrapper**: Provides a unified, Coroutine-friendly API over the AndroidX Biometric library.
- **Integrity Checker**: Hooks into Play Integrity API to detect rooted devices or tampered binaries.

## Public APIs
- `SecureStorage`: Interface for encrypting/decrypting strings and basic types.
- `BiometricAuthManager`: Triggers face or fingerprint authentication.
- `SecurityUtils`: Utilities for root detection and emulator checks.

## Configuration
Define key aliases and encryption schemes in the security configuration file. Default is `AES256_GCM`.

## Dependencies
```gradle
implementation("androidx.security:security-crypto-ktx:1.1.0-alpha06")
implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")
```

## Initialization
`SecureStorage` initializes automatically on first read/write by generating the Master Key.
```kotlin
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()
```

## Integration Steps
1. Replace usage of standard `SharedPreferences` with `SecureStorage` for sensitive data.
2. Initialize `BiometricAuthManager` in activities requiring step-up authentication.
3. Configure the backend to accept Play Integrity verdicts.

## Required Permissions
- `android.permission.USE_BIOMETRIC`
- `android.permission.USE_FINGERPRINT` (For backward compatibility)

## Manifest Entries
No special manifest entries required, other than the permissions above.

## Remote Config & Analytics Dependencies
- **Remote Config**: Can dynamically toggle strict root-detection or require biometrics for certain actions.
- **Analytics**: Log authentication failures, but **never** log passwords, tokens, or encryption keys.

## Security
- Keys are invalidated if the user disables their lock screen or changes their biometrics. Handle `KeyPermanentlyInvalidatedException` gracefully by clearing storage and prompting re-login.
- No PII is stored in plain text.

## Accessibility
Biometric prompts natively support screen readers. Ensure fallback mechanisms (e.g., PIN) have proper contrast and touch targets.

## Testing
- Use Robolectric to mock `EncryptedSharedPreferences`.
- Test biometric flows on physical devices or emulators with simulated fingerprint input.

## Migration
When migrating from plain text SharedPreferences, implement a one-time migration script that reads the old values, writes them to `SecureStorage`, and deletes the old file.

## Troubleshooting
- **Crashes on old Samsung devices**: `security-crypto` can have OEM-specific Keystore bugs. Implement a fallback to standard AES encryption using a user-derived password if the Keystore fails.
- **KeyInvalidated**: Occurs when new fingerprints are enrolled. Catch the exception and sign the user out.

## Examples
```kotlin
suspend fun authenticateUser(fragment: Fragment) {
    val result = biometricAuthManager.authenticate(
        fragment,
        title = "Unlock App",
        subtitle = "Confirm your identity"
    )
    if (result is BiometricResult.Success) {
        // Proceed
    }
}
```
