import os
import codecs

docs_dir = r"C:\Workspace\Dev\Android\codewiththiru-platform\docs\modules"
os.makedirs(docs_dir, exist_ok=True)

files = {}

files["core.md"] = """# Core Module (`:core`)

## Purpose
The `:core` module serves as the foundational layer of the CodeWithThiru Platform. It provides essential utilities, base classes, dependency injection setup, networking clients, and local persistence mechanisms. All other feature modules depend on this module.

## Architecture
- **Layer:** Data / Domain / Utilities
- **Pattern:** Clean Architecture principles.
- **Components:**
  - `di`: Hilt modules for cross-cutting dependencies.
  - `network`: Retrofit and OkHttp clients, interceptors.
  - `local`: Room database base configurations, DataStore preferences.
  - `dispatchers`: Coroutine dispatchers for testing and execution.

```mermaid
graph TD
    A[Feature Modules] -->|depends on| B(Core Module)
    B --> C[Network - Retrofit]
    B --> D[Local - Room/DataStore]
    B --> E[DI - Hilt]
```

## Public APIs
- `NetworkClient`: Configured Retrofit instance builder.
- `BaseRepository`: Abstract class for handling safe API calls and mapping responses.
- `AppDispatchers`: Interface for injecting `IO`, `Default`, and `Main` dispatchers.
- `NetworkMonitor`: Flow-based utility to monitor network connectivity.

## Configuration
Requires a valid base URL in the `build.gradle.kts` via `buildConfigField`.

## Dependencies
- Dagger Hilt (`com.google.dagger:hilt-android`)
- Retrofit & OkHttp (`com.squareup.retrofit2:retrofit`, `com.squareup.okhttp3:logging-interceptor`)
- Room (`androidx.room:room-runtime`, `androidx.room:room-ktx`)
- DataStore (`androidx.datastore:datastore-preferences`)

## Initialization
In the application class, Hilt handles the initialization of core components.
```kotlin
@HiltAndroidApp
class MainApplication : Application() {
    // Core dependencies injected automatically
}
```

## Integration Steps
1. Add `implementation(project(":core"))` to your feature module's `build.gradle.kts`.
2. Inject needed core components using `@Inject`.

## Required Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Manifest Entries
No specific `<activity>` entries.

## Remote Config & Analytics Dependencies
- Evaluates `network_timeout_ms` via Firebase Remote Config to dynamically adjust OkHttp timeouts.

## Security
- Certificate pinning configured in OkHttp via `SecurityConfig`.
- EncryptedSharedPreferences wrapper available for sensitive legacy data.

## Accessibility
N/A (No UI components in core).

## Testing
- `CoreTestModule`: Replaces standard dispatchers with `UnconfinedTestDispatcher`.
- Includes MockWebServer helpers for network testing.

## Migration
When migrating from v1 to v2:
- Replace `SharedPreferences` with `DataStore`. See `CoreMigrationHelper`.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Network calls failing | Missing `INTERNET` permission | Ensure manifest includes permission. |
| DB version mismatch | Room schema changed | Increment DB version and provide a `Migration`. |

## Examples
```kotlin
@Inject
lateinit var dispatchers: AppDispatchers

viewModelScope.launch(dispatchers.io) {
    // Background work safely executed
}
```
"""

files["designsystem.md"] = """# Design System Module (`:designsystem`)

## Purpose
The `:designsystem` module centralizes all UI styling, Jetpack Compose themes, typography, colors, and core shapes. It ensures visual consistency across the entire CodeWithThiru Platform.

## Architecture
- **Layer:** Presentation
- **Pattern:** Material Design 3 (M3) extension.
- **Components:**
  - `Theme.kt`: Main theme wrapper (`ThiruTheme`).
  - `Type.kt`: Typography scales and custom fonts.
  - `Color.kt`: Semantic color palettes for Light/Dark modes.
  - `Shape.kt`: Corner shape definitions.

## Public APIs
- `ThiruTheme`: The root composable theme wrapper.
- `ThiruTypography`: Access to typography tokens.
- `ThiruColors`: Access to custom semantic color tokens not covered by standard M3.

## Configuration
`ThemeConfig` data class can be injected to force dark/light overrides bypassing system defaults.

## Dependencies
- Compose Material 3 (`androidx.compose.material3:material3`)
- Compose UI (`androidx.compose.ui:ui`)
- Google Fonts (`androidx.compose.ui:ui-text-google-fonts`)

## Initialization
Wrap your top-level UI in the custom theme. No special initialization required at the application level.

## Integration Steps
1. Add `implementation(project(":designsystem"))`.
2. Wrap your screens in `ThiruTheme`.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- Evaluates `enable_holiday_theme` via Firebase Remote Config to toggle festive color palettes dynamically.

## Security
None.

## Accessibility
- Defines high-contrast color mappings for accessibility modes.
- Enforces minimum touch target sizes (48dp) through custom modifiers.

## Testing
- Uses Paparazzi and Roborazzi for automated snapshot testing of theming across different device form factors and dark/light modes.

## Migration
- When migrating from Material 2, ensure all `MaterialTheme.colors` calls are updated to `MaterialTheme.colorScheme`.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Colors reverting to purple | Missing Theme | Ensure `ThiruTheme` wraps the problematic composable. |
| Custom fonts not loading | Network issue | The `ui-text-google-fonts` relies on Play Services. Ensure fallback fonts are configured. |

## Examples
```kotlin
@Composable
fun MyScreen() {
    ThiruTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Text(
                text = "Hello World",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
```
"""

files["widgets.md"] = """# Widgets Module (`:widgets`)

## Purpose
The `:widgets` module houses reusable, higher-level UI components built on top of the `:designsystem`. This includes custom AppBars, BottomSheets, standardized Dialogs, Loading overlays, and Empty State views.

## Architecture
- **Layer:** Presentation
- **Pattern:** Stateless Composable functions.
- **Components:** Components are grouped by UI pattern (e.g., `/dialogs`, `/appbars`, `/states`).

## Public APIs
- `ThiruTopAppBar`: Standardized navigation bar with back handling.
- `EmptyStateView`: Configurable illustration, title, and action button.
- `LoadingOverlay`: Full-screen transparent blocking loader.
- `StandardBottomSheet`: Wrapper around M3 `ModalBottomSheet` with platform-specific adjustments.

## Configuration
Components receive configuration via data classes or standard composable parameters.

## Dependencies
- `:designsystem`
- Coil (`io.coil-kt:coil-compose`) for image loading within widgets.

## Initialization
None required.

## Integration Steps
1. Add `implementation(project(":widgets"))`.
2. Import and use the composable functions.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- `EmptyStateView` can accept remote strings configured via Firebase Remote Config.

## Security
None.

## Accessibility
- All widgets are rigorously annotated with `semantics` modifiers.
- `contentDescription` is mandatory for all image-based widgets.

## Testing
- Compose UI Testing (`createComposeRule`) is used to verify interactions (e.g., clicking the back button in `ThiruTopAppBar`).

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Images not loading in Empty State | Coil uninitialized | Ensure ImageLoader is configured or fallback drawables are provided. |

## Examples
```kotlin
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            ThiruTopAppBar(
                title = "Settings",
                onNavigationIconClick = onBack
            )
        }
    ) { padding ->
        // Content
    }
}
```
"""

files["about.md"] = """# About Module (`:about`)

## Purpose
The `:about` module provides the "About App" screen, displaying the app version, developer details, open-source licenses, and links to the Privacy Policy and Terms of Service.

## Architecture
- **Layer:** Feature
- **Pattern:** MVVM (Model-View-ViewModel).
- **Components:**
  - `AboutScreen`: The UI definition.
  - `AboutViewModel`: Handles state, version extraction, and intent construction.

## Public APIs
- `AboutScreen()`: Composable entry point.
- `aboutNavGraph()`: Navigation extension for integrating into the main app nav graph.

## Configuration
Privacy Policy and TOS URLs are injected via `BuildConfig` fields generated by the app module.

## Dependencies
- `:core`
- `:designsystem`
- `:widgets`
- Accompanist Webview (if in-app browser is used).

## Initialization
None.

## Integration Steps
1. Add `implementation(project(":features:about"))`.
2. Call `aboutNavGraph(navController)` in your main `NavHost`.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- Evaluates `about_privacy_url` and `about_tos_url` to allow updating legal links dynamically without an app update.
- Tracks `about_screen_view` in Firebase Analytics.

## Security
- External links are opened using safe Intent chooser patterns to prevent hijacking.

## Accessibility
- Uses `Modifier.semantics { heading() }` for section titles to assist screen readers.

## Testing
- Unit tests for `AboutViewModel` checking that Intents are formulated correctly.

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Legal links crashing app | No browser installed | Wrap `startActivity` in a `try/catch` for `ActivityNotFoundException`. |

## Examples
```kotlin
// In your MainNavGraph.kt
NavHost(...) {
    // Other destinations
    aboutNavGraph(navController)
}
```
"""

files["feedback.md"] = """# Feedback Module (`:feedback`)

## Purpose
The `:feedback` module allows users to submit bug reports, suggestions, or contact support directly from within the application. It supports device info collection and screenshot attachments.

## Architecture
- **Layer:** Feature
- **Pattern:** MVVM with Clean Architecture.
- **Components:**
  - `FeedbackScreen`: UI for the feedback form.
  - `FeedbackViewModel`: Validates input and triggers submission.
  - `FeedbackRepository`: Handles the API request to the support backend.

## Public APIs
- `FeedbackScreen()`: The main UI composable.
- `FeedbackManager`: Singleton utility to trigger silent logs or contextual feedback triggers.

## Configuration
Provide the support API endpoint and API key via Hilt bindings.

## Dependencies
- `:core`
- `:widgets`
- `androidx.activity:activity-compose` (for file pickers).

## Initialization
None.

## Integration Steps
1. Add `implementation(project(":features:feedback"))`.
2. Add `feedbackNavGraph()` to your app's navigation.

## Required Permissions
To attach existing media files:
```xml
<!-- For Android 12 and below -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<!-- For Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
```

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- `enable_feedback_attachments`: Remote config toggle to disable image uploads if server load is high.
- Tracks `feedback_submitted` events.

## Security
- **PII Scrubbing:** `FeedbackManager` strips out recognized email formats and phone numbers from logs before transmitting.

## Accessibility
- Form fields specify `imeAction = ImeAction.Next` for smooth keyboard traversal.

## Testing
- `FeedbackRepositoryTest`: Mocks the support backend API.
- `PIIScrubberTest`: Ensures regex correctly removes sensitive data.

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Upload fails immediately | Payload too large | Ensure bitmaps are compressed before sending (max 2MB). |

## Examples
```kotlin
Button(onClick = { navController.navigate("feedback_route") }) {
    Text("Report a Bug")
}
```
"""

files["rating.md"] = """# Rating Module (`:rating`)

## Purpose
The `:rating` module manages in-app review prompts using the Google Play Core API. It abstracts the complex logic of deciding *when* to prompt the user to ensure it's not intrusive.

## Architecture
- **Layer:** Domain/Utilities
- **Pattern:** Manager / Repository pattern.
- **Components:**
  - `AppRatingManager`: Encapsulates Play Core `ReviewManager`.
  - `RatingRulesEngine`: Evaluates conditions (sessions, positive actions) to determine eligibility.

## Public APIs
- `AppRatingManager.checkAndShowRating(activity: Activity)`: Evaluates rules and shows prompt if eligible.
- `AppRatingManager.logPositiveAction()`: Increments the internal counter of happy paths (e.g., successful task completion).

## Configuration
Requires thresholds to be configured (e.g., min 5 sessions, min 3 positive actions).

## Dependencies
- `:core`
- Play Review (`com.google.android.play:review`, `com.google.android.play:review-ktx`)

## Initialization
Inject `AppRatingManager` via Hilt. No explicit startup initialization required.

## Integration Steps
1. Add `implementation(project(":features:rating"))`.
2. Call `logPositiveAction()` when a user completes a core flow.
3. Call `checkAndShowRating()` at a natural pause in the app (e.g., returning to the home screen).

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- Evaluates `rating_min_sessions` and `rating_prompt_delay_days` to remotely adjust aggressively prompting users.
- Tracks `in_app_review_shown` and `in_app_review_completed`.

## Security
None.

## Accessibility
- UI is entirely managed by the Android OS (Google Play Services), ensuring compliance.

## Testing
- Use `FakeReviewManager` provided by Play Core for instrumented tests.
- Unit test `RatingRulesEngine` by mocking `DataStore` counters.

## Migration
- Ensure migration from older Play Core monolithic library to the specialized `com.google.android.play:review` artifact.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Prompt never shows | Quota exhausted / Rules | Play Store limits prompts. Use Internal App Sharing to force show it for testing. |

## Examples
```kotlin
// In MainActivity.kt or a ViewModel connected to UI events
fun onUserCompletedTask() {
    ratingManager.logPositiveAction()
    
    lifecycleScope.launch {
        ratingManager.checkAndShowRating(this@MainActivity)
    }
}
```
"""

files["more-apps.md"] = """# More Apps Module (`:more-apps`)

## Purpose
The `:more-apps` module provides a cross-promotion screen to showcase other applications developed by the CodeWithThiru team. It fetches app metadata from a remote JSON source.

## Architecture
- **Layer:** Feature
- **Pattern:** MVVM
- **Components:**
  - `MoreAppsScreen`: LazyColumn displaying apps.
  - `MoreAppsViewModel`: Fetches data and maps UI state.
  - `MoreAppsRepository`: Handles network calls to fetch the JSON payload.

## Public APIs
- `MoreAppsScreen()`
- `moreAppsNavGraph()`

## Configuration
Requires the endpoint URL defining the apps JSON payload.

## Dependencies
- `:core`, `:designsystem`, `:widgets`
- Coil for app icons.

## Initialization
None.

## Integration Steps
1. Add `implementation(project(":features:more-apps"))`.
2. Expose the entry point in your app's drawer or settings menu.

## Required Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Manifest Entries
To determine if an app is already installed and change the CTA to "Open" instead of "Install":
```xml
<queries>
    <package android:name="com.codewiththiru.app1" />
    <package android:name="com.codewiththiru.app2" />
</queries>
```

## Remote Config & Analytics Dependencies
- `more_apps_json_url`: Remotely configure the source URL.
- Tracks `cross_promo_clicked` with the destination package name.

## Security
- The repository strictly validates the incoming JSON against expected data classes to prevent malformed data injection.

## Accessibility
- Content descriptions on app icons (`"Icon for ${app.name}"`).
- Clear semantics for the "Install" / "Open" buttons.

## Testing
- Use MockWebServer to simulate the JSON payload.

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Buttons say "Install" when app is installed | Missing `<queries>` | Android 11+ requires package visibility declarations. |

## Examples
```kotlin
// JSON Structure expected by Repository:
[
  {
    "id": "com.codewiththiru.notes",
    "name": "Thiru Notes",
    "description": "A beautiful note taking app.",
    "iconUrl": "https://...",
    "playStoreUrl": "https://play.google.com/..."
  }
]
```
"""

files["updates.md"] = """# Updates Module (`:updates`)

## Purpose
The `:updates` module seamlessly handles in-app updates using the Google Play Core App Update API. It supports both Flexible (background download) and Immediate (forced blocking) update flows.

## Architecture
- **Layer:** Domain/Utilities
- **Pattern:** Wrapper/Manager.
- **Components:**
  - `UpdateManager`: Abstracts the interaction with `AppUpdateManager`.

## Public APIs
- `UpdateManager.checkForUpdate(activity, updateType)`
- `UpdateManager.registerListener()`: For tracking flexible download progress.

## Configuration
Requires mapping of Update Priorities (assigned in Google Play Console during release) to update types (Flexible vs Immediate).

## Dependencies
- `:core`
- App Update (`com.google.android.play:app-update`, `com.google.android.play:app-update-ktx`)

## Initialization
Usually instantiated in `MainActivity` since it requires Activity context to launch the update flow.

## Integration Steps
1. Add `implementation(project(":features:updates"))`.
2. In `MainActivity.onCreate`, instantiate and call `checkForUpdate()`.
3. Override `onActivityResult` (or use `ActivityResultContracts`) to handle update failures or cancellations.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- `force_update_version_code`: A fallback remote config to force an immediate update if the Play Store priority system fails or takes too long to propagate.
- Tracks `update_prompt_shown`, `update_accepted`, `update_failed`.

## Security
- Relies on Google Play Services signature verification for the APKs.

## Accessibility
- Handled by Google Play Services dialogs.

## Testing
- Utilize `FakeAppUpdateManager` to simulate updates in Espresso tests without actually contacting the Play Store.

## Migration
- Moved away from the monolithic Play Core library to the standalone `app-update` dependency.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Update not triggering | Play Store Cache | Clear cache of the Google Play Store app on the testing device. |
| Immediate update stuck | Lifecycle issue | Ensure `checkForUpdate` is also called in `onResume` to resume an immediate update if the app was backgrounded. |

## Examples
```kotlin
class MainActivity : ComponentActivity() {
    @Inject lateinit var updateManager: UpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check for updates
        lifecycleScope.launch {
            updateManager.checkForUpdate(
                activity = this@MainActivity,
                allowedType = AppUpdateType.FLEXIBLE
            )
        }
    }
    
    override fun onResume() {
        super.onResume()
        updateManager.resumeImmediateUpdateIfInProgress(this)
    }
}
```
"""

files["coupons.md"] = """# Coupons Module (`:coupons`)

## Purpose
The `:coupons` module provides the UI and logic for promo code redemption. This allows users to unlock premium features by entering a code, integrating directly with Google Play Billing.

## Architecture
- **Layer:** Feature / Billing Domain
- **Pattern:** MVVM
- **Components:**
  - `CouponRedemptionDialog`: UI for entering the code.
  - `CouponManager`: Interfaces with the `:billing` (or `:core`) module to validate the code.

## Public APIs
- `CouponRedemptionDialog()`
- `CouponManager.redeemCode(code: String)`

## Configuration
Requires Play Console promo codes to be set up and associated with in-app products or subscriptions.

## Dependencies
- `:core`, `:designsystem`, `:widgets`
- Google Play Billing (`com.android.billingclient:billing-ktx`)

## Initialization
Billing Client must be connected prior to validating a code.

## Integration Steps
1. Add `implementation(project(":features:coupons"))`.
2. Provide an entry point (e.g., a "Redeem Code" button in the Premium Upgrade screen).

## Required Permissions
```xml
<uses-permission android:name="com.android.vending.BILLING" />
```

## Manifest Entries
None specifically for coupons, but Billing permission is required.

## Remote Config & Analytics Dependencies
- `enable_promo_codes`: Allows turning off the feature remotely if abuse is detected.
- Tracks `promo_code_attempted`, `promo_code_success`, `promo_code_invalid`.

## Security
- Client-side validation is insufficient. The app must fetch the updated purchase token from Play Billing and optionally verify it server-side.

## Accessibility
- Text fields have appropriate `keyboardOptions` and error state readouts.

## Testing
- Test using Play Store License Testing accounts with generated test codes.
- Unit test `CouponManager` by mocking the `BillingClient` responses.

## Migration
- Ensure compatibility with Billing Library v6/v7 standards.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Code invalid | Skus mismatch / Inactive | Verify the code is active in the Play Console and associated with a base plan. |
| Nothing happens on success | Cache issue | Query purchases explicitly after redemption to update UI state. |

## Examples
```kotlin
@Composable
fun UpgradeScreen() {
    var showCouponDialog by remember { mutableStateOf(false) }

    Button(onClick = { showCouponDialog = true }) {
        Text("Redeem Promo Code")
    }

    if (showCouponDialog) {
        CouponRedemptionDialog(
            onDismiss = { showCouponDialog = false },
            onSuccess = { /* Unlock premium UI */ }
        )
    }
}
```
"""

files["analytics.md"] = """# Analytics Module (`:analytics`)

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
"""

for filename, content in files.items():
    with codecs.open(os.path.join(docs_dir, filename), "w", encoding="utf-8") as f:
        f.write(content)

print(f"Successfully generated {len(files)} files in {docs_dir}")
