# Remote Config Module

## Purpose
The `remote-config` module provides a centralized mechanism for managing dynamic feature toggles, A/B testing parameters, and configuration updates without requiring users to download an app update. It is a core foundational module in the CodeWithThiru Platform.

## Architecture
This module implements a Singleton Repository pattern that wraps Firebase Remote Config. It exposes configuration parameters via Kotlin `Flow`, allowing the UI and other modules to reactively update when configurations change. A local fallback XML is provided to ensure safe defaults.

## Public APIs
- `RemoteConfigManager`: The primary entry point for fetching and observing configurations.
- `FeatureToggle`: Enum or sealed class defining all available feature flags.
- `getConfigValue(key: String): Flow<ConfigValue>`: Observes a specific configuration key.
- `getBoolean(toggle: FeatureToggle): Boolean`: Synchronously retrieves a boolean feature flag.
- `fetchAndActivate(): suspend Result<Boolean>`: Forces a fetch from the server.

## Configuration
Define local default values in `res/xml/remote_config_defaults.xml`. Corresponding keys must be set up in the Firebase Console under "Remote Config".

## Dependencies
```gradle
implementation("com.google.firebase:firebase-config-ktx:21.6.0")
```

## Initialization
Initialization occurs during app startup within the App class or Dependency Injection container (e.g., Hilt `SingletonComponent`).
```kotlin
remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
// Fetch with a minimum fetch interval (e.g., 1 hour in prod, 0 in dev)
remoteConfig.fetchAndActivate()
```

## Integration Steps
1. Apply the `com.google.gms.google-services` plugin.
2. Add `google-services.json` to your app module.
3. Inject `RemoteConfigManager` into your ViewModels or UseCases.
4. Set minimum fetch intervals based on build variants (Debug vs Release).

## Required Permissions
- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`

## Manifest Entries
No specific manifest entries are required beyond standard Firebase initialization components.

## Remote Config & Analytics Dependencies
This module is a dependency for almost all other modules (e.g., controlling ad frequency, enabling AI features). Analytics logs fetch events and activation status.

## Security
Do not store Personally Identifiable Information (PII), secrets, or API keys in Remote Config. It is not encrypted and can be intercepted or read from the APK.

## Accessibility
N/A. This module operates in the background. However, feature toggles can be used to enable/disable experimental accessibility features.

## Testing
- Unit tests: Mock `RemoteConfigManager` to return desired configurations.
- UI tests: Use developer overrides or local default XML files to force specific UI states.

## Migration
N/A (Core module starting from v1.0).

## Troubleshooting
- **Configs not updating**: Check the `minimumFetchIntervalInSeconds`. Firebase throttles frequent requests. Ensure you use `fetchAndActivate()`.
- **Default values applied instead of remote**: Verify the exact key string matches the Firebase Console.

## Examples
```kotlin
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val isNewFeatureEnabled by viewModel.isNewFeatureEnabled.collectAsState()
    
    if (isNewFeatureEnabled) {
        NewFeatureComponent()
    } else {
        LegacyComponent()
    }
}
```
