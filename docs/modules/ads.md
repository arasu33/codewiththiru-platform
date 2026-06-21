# Ads Module

## Purpose
The `ads` module handles monetization through Google Mobile Ads (AdMob) and manages user consent using the User Messaging Platform (UMP). It provides seamless integrations for Banner, Interstitial, Rewarded, and App Open ads in both Compose and View-based UIs.

## Architecture
The module follows a sequential initialization architecture:
1. UMP SDK checks consent status (GDPR/CPRA).
2. If consent is granted or not required, AdMob SDK is initialized.
3. Ad repositories pre-load formats like Interstitials to minimize latency.
4. Ad presentation is decoupled via interfaces to prevent memory leaks.

## Public APIs
- `AdManager`: Handles initialization and consent.
- `ComposeBannerAd`: A Jetpack Compose wrapper for `AdView`.
- `InterstitialAdController`: Pre-loads and shows full-screen ads.
- `RewardedAdController`: Manages rewarded video ads and callback rewards.

## Configuration
Ad unit IDs are configured via the `remote-config` module or directly in `strings.xml`. Test ad units must be used in Debug builds.

## Dependencies
```gradle
implementation("com.google.android.gms:play-services-ads:23.0.0")
implementation("com.google.android.ump:user-messaging-platform:2.2.0")
```

## Initialization
Must be initialized on the main thread in the launching Activity.
```kotlin
ConsentInformation.getInstance(context).requestConsentInfoUpdate(
    activity,
    params,
    { /* On success, load ads */ },
    { /* On failure */ }
)
```

## Integration Steps
1. Add the AdMob App ID to `AndroidManifest.xml`.
2. Configure UMP in the Google AdMob console.
3. Initialize `AdManager` in the `MainActivity`.
4. Place `ComposeBannerAd` in screen layouts.

## Required Permissions
- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`
- `com.google.android.gms.permission.AD_ID` (For targeted advertising)

## Manifest Entries
```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="${adMobAppId}"/>
```

## Remote Config & Analytics Dependencies
- **Remote Config**: Toggles ad formats, frequency caps, and ad-free premium states.
- **Analytics**: Logs `ad_impression`, `ad_click`, and `ad_revenue` events.

## Security
Ad placement must comply with AdMob policies (no overlapping content, safe padding). Prevent accidental clicks to avoid account bans.

## Accessibility
Ensure `ComposeBannerAd` has appropriate `contentDescription` for screen readers (e.g., "Advertisement").

## Testing
Always use Google's Test Ad Unit IDs (`ca-app-pub-3940256099942544/6300978111`) and register test devices via `RequestConfiguration.Builder().setTestDeviceIds(...)`.

## Migration
When migrating from older AdMob versions, ensure standard View ad lifecycles (`resume`, `pause`, `destroy`) are mapped properly to Compose `DisposableEffect`.

## Troubleshooting
- **Error Code 3 (No Fill)**: Normal during development. Means the request was successful but no ad was available.
- **Consent form not showing**: Verify the Geography settings in UMP dashboard or force test geography to EEA.

## Examples
```kotlin
@Composable
fun BannerAdContainer() {
    ComposeBannerAd(
        adUnitId = stringResource(id = R.string.banner_ad_unit),
        modifier = Modifier.fillMaxWidth()
    )
}
```
