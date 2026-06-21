# Security Guide

The CodeWithThiru Platform adheres to strict security standards to ensure the safety of user data and application integrity. This document outlines the security architecture, threat model, and best practices for developers.

## Threat Model

When designing features for the platform, consider the following primary threat vectors:
1.  **Network Interception (Man-in-the-Middle):** Attackers attempting to intercept API calls or modify payloads.
2.  **Local Data Extraction:** Malicious apps or users attempting to read sensitive data stored on the device.
3.  **Reverse Engineering:** Attackers analyzing the APK to extract API keys or find vulnerabilities.
4.  **Unauthorized Access:** Exploitation of authentication flows to gain access to user accounts.

## OWASP Mobile Top 10 Mitigation

The platform is designed with the OWASP Mobile Top 10 in mind:
*   **M1: Improper Platform Usage:** We strictly follow Android component guidelines (intents, permissions).
*   **M2: Insecure Data Storage:** See the *Encryption & Storage* section below.
*   **M3: Insecure Communication:** All API communications mandate TLS 1.2+. Network Security Configuration prevents cleartext traffic.
*   **M4: Insecure Authentication:** Managed via Firebase Authentication with secure session tokens.
*   **M5: Insufficient Cryptography:** We rely on Tink and Android Keystore; custom crypto is prohibited.
*   **M8: Code Tampering:** ProGuard/R8 is enforced for release builds to obfuscate code and deter tampering.

## PII (Personally Identifiable Information) Handling

Protecting PII is paramount:
*   **No Logging of PII:** Never log user names, email addresses, phone numbers, passwords, or tokens using `Log.d` or Timber. Use static strings or hashed values if necessary.
*   **Data Minimization:** Only request data that is strictly required for the feature.
*   **Redaction:** Redact PII in crash reporting tools (e.g., Firebase Crashlytics).

## Encryption & Storage

### SharedPreferences / DataStore
Never store raw sensitive data in standard SharedPreferences.
*   **EncryptedSharedPreferences:** Use the Jetpack Security library's `EncryptedSharedPreferences` for storing access tokens, API keys, and user preferences that contain sensitive state.
*   **Proto DataStore:** If using DataStore, integrate it with Tink for encrypting the underlying file.

### Database Encryption
For Room databases containing sensitive user data (e.g., cached messages, health data):
*   Use **SQLCipher** for Android to encrypt the entire SQLite database file.
*   Generate a secure 256-bit key using the Android Keystore system to encrypt the database.

### Network Security Configuration
A custom `network_security_config.xml` is defined in the `core-network` module:
```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">api.codewiththiru.com</domain>
        <pin-set expiration="2027-01-01">
            <pin digest="SHA-256">7HIpactkIAq2Y49orFOOQKurWxmmSFZhBCoQYcRhJ3Y=</pin>
        </pin-set>
    </domain-config>
</network-security-config>
```

## Review Checklist for Developers
- [ ] Are new permissions absolutely necessary?
- [ ] Is PII excluded from logs and analytics?
- [ ] Are newly added API endpoints using HTTPS?
- [ ] Is sensitive state stored in EncryptedSharedPreferences?
