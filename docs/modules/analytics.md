# Analytics Module (`:analytics`)

## Purpose
The `:analytics` module provides a unified interface for event tracking, screen views, user properties, and crash reporting. It abstracts the underlying providers (Firebase Analytics, Crashlytics).

## Architecture
- **Layer:** Core Utilities
- **Pattern:** Facade Pattern.
- **Components:**
  - `AnalyticsTracker`: Interface defining logging capabilities.
  - `FirebaseAnalyticsImpl`: Concrete implementation for Firebase.
  - `EventBuilder`: DSL for constructing type-safe events.

## Public APIs
- `AnalyticsTracker.logEvent(name: String, params: Map<String, Any>)`
- `AnalyticsTracker.logScreenView(screenName: String)`
- `AnalyticsTracker.setUserProperty(key: String, value: String)`
- `AnalyticsTracker.recordException(throwable: Throwable)`

## Configuration
Requires `google-services.json` in the app module.

## Dependencies
- Firebase Analytics (`com.google.firebase:firebase-analytics-ktx`)
- Firebase Crashlytics (`com.google.firebase:firebase-crashlytics-ktx`)

## Initialization
Initialized via Hilt module. Crashlytics automatically initializes via ContentProvider, but can be configured dynamically.

## Integration Steps
1. Add `implementation(project(":analytics"))`.
2. Inject `AnalyticsTracker` where needed.
3. Apply `com.google.gms.google-services` plugin in your `app/build.gradle.kts`.

## Required Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Manifest Entries
```xml
<meta-data
    android:name="firebase_analytics_collection_enabled"
    android:value="false" /> <!-- Turned on programmatically after consent -->
```

## Remote Config & Analytics Dependencies
- `analytics_collection_enabled`: Main toggle for GDPR/CCPA compliance.

## Security
- **PII Guard:** `FirebaseAnalyticsImpl` implements a strict filter to catch strings resembling emails, phone numbers, or credit cards in event parameters to prevent PII leakage.

## Accessibility
N/A

## Testing
- `FakeAnalyticsTracker` provided for unit testing ViewModels without Firebase dependencies.
- Use `adb shell setprop debug.firebase.analytics.app com.codewiththiru.app` to use Firebase DebugView.

## Migration
- Event mapping updated to comply with Google Analytics 4 (GA4) standards.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Events not showing in dashboard | Batching | Firebase batches events. Use DebugView to see real-time logs. |
| Missing user properties | Session timing | Set user properties immediately upon login to ensure they tag subsequent events. |

## Examples
```kotlin
class HomeViewModel @Inject constructor(
    private val analytics: AnalyticsTracker
) : ViewModel() {

    init {
        analytics.logScreenView("Home")
    }

    fun onPurchaseClicked() {
        analytics.logEvent("purchase_click", mapOf("item_id" to "premium_sub"))
    }
}
```
