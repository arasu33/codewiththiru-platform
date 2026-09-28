# Enterprise Ads Module

The `ads` module is a fully abstracted, provider-agnostic framework for integrating, managing, and monetizing with advertisements in Android applications. It decouples the core application logic from the underlying Ad networks (such as Google AdMob) to prevent vendor lock-in, ensure a crash-free experience, and allow over-the-air remote configuration.

## Architecture Highlights

1. **`AdsManager` (API Layer)**: The single entry point for all feature modules to request or show ads. It routes requests securely.
2. **`AdsRepository` (Data/State Layer)**: Manages state caching, load requests, and lifecycle transitions.
3. **`AdsProvider` (Provider Layer)**: The concrete implementation (`AdMobAdsProvider`) that talks to the specific SDK. This makes switching from AdMob to AppLovin MAX as simple as swapping the injected provider implementation.
4. **`AdsConfig` & `PlatformAdUnitResolver` (Configuration & Security)**: Resolves ad toggles dynamically (Remote Config overrides) and securely maps environment states (`Debug`, `Production`) to prevent test leakages.
5. **`AnalyticsManager` Integration**: All ad impressions, clicks, loads, and revenues are automatically piped into the centralized `analytics` module.

## Supported Formats
- **Banner / Adaptive Banner**: In-line UI ads.
- **Interstitial**: Full-screen blocking ads with frequency and policy capping.
- **Rewarded / Rewarded Interstitial**: Opt-in video ads granting users rewards.
- **Native Advanced**: Customizable ads mapping to custom View hierarchies.
- **App Open**: Splash screen / app return ads bounded by a strict 4-hour expiration policy.

## ⚠️ Consumer Prerequisites

Before using this module, your consumer app **must** complete the following setup:

### 1. AdMob Application ID (AndroidManifest.xml)
> ⚠️ **CRITICAL**: The Google Mobile Ads SDK **will crash on startup** (`IllegalStateException`) if this `<meta-data>` tag is missing from your app's `AndroidManifest.xml` inside `<application>`:

```xml
<application>
    <!-- Replace with your actual AdMob App ID from Google AdMob Console -->
    <!-- For testing/development, use the Google test app ID: ca-app-pub-3940256099942544~3347511713 -->
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="ca-app-pub-3940256099942544~3347511713"/>
</application>
```

### 2. Permissions
Add to your app's `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />
```

### 3. BOM Dependency (`build.gradle.kts`)
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:ads")
}
```

## Usage

### 1. Initialization
The Ads SDK must be initialized early (e.g., in Application class) before loading any ads.
```kotlin
adsManager.initialize()
```

### 2. Loading an Ad
```kotlin
// Example: Preloading an Interstitial Ad
adsManager.load(AdType.Interstitial)
```

### 3. Showing an Ad
```kotlin
// Example: Showing a Rewarded Ad
adsManager.show(AdType.Rewarded, activity)
```

### 4. Configuration Overrides (Remote Config)
You can disable all ads globally or kill a specific ad format via the `AdsRemoteConfig`:
```json
{
  "ads_enabled": false,
  "ads_interstitial_enabled": false
}
```

## Security & Hardening
- **ProGuard**: Custom keep rules in `proguard-rules.pro` ensure SDK stability.
- **Memory Leaks**: All loaders (`AdMobInterstitialLoader`, etc.) explicitly release contexts (`destroy()`) upon dismissal.
- **ID Validation**: `PlatformAdUnitResolver` enforces `IllegalStateException` if production Ad IDs are loaded in a Debug environment, protecting the publisher account from invalid test clicks.

## Commands
Run the following to test the module:
```bash
./gradlew :ads:build
./gradlew :ads:lintDebug
```
