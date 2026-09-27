# 🛡️ Consent Module (`:consent`)

Privacy consent manager and UI components adhering to GDPR, CCPA, and telemetry privacy regulations.

## Features
- **Consent Categories**: Granular control over `NECESSARY`, `ANALYTICS`, `ADVERTISING`, and `FUNCTIONAL` data processing.
- **Persistent Storage**: Backed by Jetpack DataStore Preferences (`DataStoreConsentManager`).
- **Reactive State**: Exposes `Flow<ConsentSnapshot>` for instant response across app services.
- **Jetpack Compose UI**: Pre-styled `ConsentBanner` with customizable messaging and accept/decline actions.

## Quick Start
```kotlin
// 1. Dependency
implementation("com.codewiththiru.platform:consent")

// 2. Manager
val consentManager: ConsentManager = DataStoreConsentManager(context)

// 3. Render Banner in Compose
ConsentBanner(
    onAcceptAll = { coroutineScope.launch { consentManager.grantAll() } },
    onDeclineOptional = { coroutineScope.launch { consentManager.denyAll() } }
)
```
