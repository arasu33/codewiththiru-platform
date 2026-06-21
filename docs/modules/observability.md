# Observability Module

## Purpose
The `observability` module is responsible for application telemetry, including crash reporting, performance monitoring, and structured logging. It provides insights into app health and user experience in production environments.

## Architecture
- **Logger Interface**: An abstraction over standard Android logging. Implementations route logs to Logcat (Debug) and remote services (Release).
- **Crashlytics Tree**: A Timber Tree that forwards warnings and errors to Firebase Crashlytics as non-fatal exceptions.
- **Performance Interceptors**: OkHttp interceptors that log network latency to Firebase Performance Monitoring.

## Public APIs
- `Logger.d(msg)`, `Logger.i(msg)`, `Logger.w(msg)`, `Logger.e(exception, msg)`
- `PerformanceTracker`: API to start and stop custom trace metrics.
- `CrashReporter`: API to set custom user keys and log non-fatal exceptions.

## Configuration
Define logging verbosity levels based on build variants. Enable ProGuard mapping uploads for release builds.

## Dependencies
```gradle
implementation("com.google.firebase:firebase-crashlytics-ktx:18.6.2")
implementation("com.google.firebase:firebase-perf-ktx:20.5.2")
implementation("com.jakewharton.timber:timber:5.0.1")
```

## Initialization
Initialize Timber trees in the `Application` class.
```kotlin
if (BuildConfig.DEBUG) {
    Timber.plant(Timber.DebugTree())
} else {
    Timber.plant(CrashlyticsTree())
}
```

## Integration Steps
1. Apply the Crashlytics and Performance Gradle plugins.
2. Replace all `Log.d()` calls with `Logger.d()`.
3. Add the `FirebasePerformanceInterceptor` to your Retrofit/OkHttp clients.

## Required Permissions
- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`

## Manifest Entries
Enable or disable automatic collection via meta-data:
```xml
<meta-data
    android:name="firebase_crashlytics_collection_enabled"
    android:value="${enableCrashReporting}" />
```

## Remote Config & Analytics Dependencies
- **Remote Config**: Dynamically adjust log levels or disable performance tracking if it causes overhead.
- **Analytics**: Crashlytics auto-integrates with Google Analytics to show the "crash-free users" metric.

## Security
**Critical**: Implement log masking. Ensure that emails, passwords, auth tokens, and financial data are never passed to `Logger`.

## Accessibility
N/A. Operates entirely in the background.

## Testing
- Force a crash using `FirebaseCrashlytics.getInstance().crash()` to verify the integration.
- Unit tests can mock the `Logger` to verify correct log emission without hitting real APIs.

## Migration
When migrating from legacy logging tools (like Bugsnag or Sentry), ensure the `Logger` interface is implemented by the new provider to avoid touching business logic.

## Troubleshooting
- **Obfuscated Stack Traces**: Ensure the Crashlytics Gradle plugin is correctly uploading mapping files (`mapping.txt`).
- **Missing Logs**: Crashlytics batches logs and sends them on the *next* app launch.

## Examples
```kotlin
try {
    processPayment()
} catch (e: PaymentException) {
    Logger.e(e, "Payment failed for user tier: ${user.tier}")
    // Recover state
}
```
