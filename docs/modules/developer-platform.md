# Developer Platform Module

## Purpose
The `developer-platform` module provides internal tools, debug menus, and networking inspection utilities specifically designed for developers and QA testers. It significantly accelerates the debugging and testing processes during development.

## Architecture
- **Variant-Aware Implementation**: The module uses Gradle build variants (`debug` vs `release`) to ensure that debugging tools are completely stripped from the production build.
- **Debug Drawer**: A hidden UI component accessible via a secret gesture or deep link.
- **Network Interception**: Uses Chucker to intercept, store, and display all incoming and outgoing HTTP traffic.

## Public APIs
- `DeveloperMenuController`: Manages the visibility and state of the debug drawer.
- `MockServerConfigurator`: Allows developers to route API calls to local mock servers or staging environments.
- `FeatureFlagOverride`: Local overrides for `remote-config` flags.

## Configuration
All configuration for this module is strictly internal and should be disabled by default in `release` builds.

## Dependencies
```gradle
debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
```

## Initialization
Initialization occurs only in the `debug` build variant inside the Application class.
```kotlin
if (BuildConfig.DEBUG) {
    ChuckerCollector(context)
    DeveloperMenuController.init(context)
}
```

## Integration Steps
1. Add the Chucker interceptor to your base OkHttp client.
2. Embed the `DebugDrawer` Compose component in the root layout of your main activity (only visible in debug).
3. Connect `FeatureFlagOverride` to your `RemoteConfigManager` to allow local toggling.

## Required Permissions
- `android.permission.POST_NOTIFICATIONS` (Chucker uses notifications to alert developers of network requests).

## Manifest Entries
Chucker automatically merges its own activities into the manifest for the debug build.

## Remote Config & Analytics Dependencies
- **Remote Config**: The developer platform allows overriding these configs locally to test A/B test variants without changing Firebase settings.
- **Analytics**: Analytics events can be routed to logcat instead of Firebase when a developer toggle is active.

## Security
**Critical**: Ensure that `debugImplementation` and `releaseImplementation (...no-op)` are strictly adhered to. Shipping developer tools (like Chucker) in a release build exposes sensitive network headers (e.g., Bearer tokens) to end users.

## Accessibility
Developer tools do not need to meet strict accessibility standards, though basic contrast and touch targets are recommended for QA ease of use.

## Testing
- Unit testing is generally not required for developer tools.
- Ensure the `release` build compiles successfully and that `DeveloperMenuController` acts as a no-op.

## Migration
N/A. Keep tools updated with the latest Android SDKs to ensure compatibility.

## Troubleshooting
- **Chucker Notifications Not Showing**: Ensure the app has the `POST_NOTIFICATIONS` permission granted on Android 13+ devices.
- **Release Build Fails**: Verify that no production code imports `com.chuckerteam.chucker.api.ChuckerInterceptor` directly. Use dependency injection to provide the interceptor only in debug.

## Examples
```kotlin
// In OkHttpClient setup
val clientBuilder = OkHttpClient.Builder()
if (BuildConfig.DEBUG) {
    clientBuilder.addInterceptor(ChuckerInterceptor.Builder(context).build())
}
```
