# Analytics API Guide

## 1. Setup

Add the module to your `build.gradle.kts`:
```kotlin
implementation(project(":analytics"))
```

## 2. Core Models

### `AnalyticsEvent`
```kotlin
val event = AnalyticsEvent(
    name = "lesson_completed",
    parameters = mapOf("lesson_id" to "123", "score" to 95)
)
```

## 3. Tracking

Obtain an instance of `AnalyticsManager` and track events:
```kotlin
analyticsManager.track(event)
analyticsManager.trackScreen(AnalyticsScreen("Home"))
analyticsManager.setUserProperty(AnalyticsUserProperty("tier", "pro"))
```

## 4. Consent

Manage user tracking consent:
```kotlin
analyticsConsentManager.grant()
analyticsConsentManager.deny()
```
*Note: Denying consent immediately drops all tracking requests and clears the offline queue.*
