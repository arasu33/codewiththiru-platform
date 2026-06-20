# More Apps API Guide

This guide details how to integrate and configure the `:more-apps` module.

## Configuration

`MoreAppsConfig` is the primary entry point to customize the UI.

```kotlin
val config = MoreAppsConfig(
    displayType = MoreAppsDisplayType.Grid,
    enableSearch = true,
    enableCategories = true,
    cachePolicy = CachePolicy.CacheFirst,
    emptyStateConfig = MoreAppsEmptyStateConfig(
        title = "No Apps Found",
        message = "Check back later for more apps.",
        buttonText = "Retry"
    )
)
```

## Analytics Integration

Implement `MoreAppsAnalyticsProvider` to route events to your analytics SDK (e.g. Firebase, Mixpanel).

```kotlin
class MyAnalytics : MoreAppsAnalyticsProvider {
    override fun logAppAction(app: MoreAppModel, action: AppActionType, position: Int, section: String) {
        // Log to Firebase
    }
    // ...
}
```

## Image Loading

Implement `MoreAppsImageProvider` using your preferred library.

```kotlin
class CoilImageProvider : MoreAppsImageProvider {
    @Composable
    override fun LoadImage(url: String, contentDescription: String?, modifier: Modifier) {
        AsyncImage(model = url, contentDescription = contentDescription, modifier = modifier)
    }
}
```
