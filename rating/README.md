# ⭐ Rating Module

Behavioral in-app rating prompt engine adhering to Google Play In-App Review guidelines.

## ⚠️ Consumer Prerequisites
- Requires a **signed build uploaded to the Google Play Console**.
- Play In-App Review dialog will **silently fail** on debug builds, sideloaded APKs, or emulators without the Play Store.

## Features
- **Smart Triggers**: Prompt users only after N launches or significant events.
- **Cooldowns**: Respects Play Store guidelines for prompt frequency.
- **Interception**: Redirects 1-4 star ratings to your internal feedback loop.
- **Native Integration**: Seamlessly calls `review-ktx` for 5-star ratings.

## Quick Start
1. Add dependency:
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:rating")
}
```

2. Record events in your app:
```kotlin
// In MainActivity.onCreate
ratingRepository.recordAppLaunch()

// When user achieves something
ratingRepository.recordSignificantEvent()
```

3. Render prompt when eligible:
```kotlin
val config = RatingConfig.Builder()
    .setRules(minimumAppLaunches = 5, requiredSignificantEvents = 2)
    .build()

// Standard Compose Dialog or BottomSheet will render based on config
RatingScreen(config = config)
```
