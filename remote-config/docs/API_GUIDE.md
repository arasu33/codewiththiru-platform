# Remote Config API Guide

## Initialization
The `RemoteConfigManager` acts as the facade. Use `DefaultRemoteConfigManager`.

```kotlin
val manager = DefaultRemoteConfigManager(providers, cache, metrics, recovery)
manager.initialize()
```

## Reading Values
Use the typed accessor methods. It evaluates caches and fallbacks transparently.

```kotlin
val adsEnabled = manager.getBoolean("ads_enabled", false)
val retryCount = manager.getInt("max_retries", 3)
```

## Observing State
```kotlin
manager.state.collect { state ->
    when (state) {
        is RemoteConfigState.Ready -> { /* Values available */ }
        is RemoteConfigState.Error -> { /* Handle outage */ }
    }
}
```
