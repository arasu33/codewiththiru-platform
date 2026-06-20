# Migration Guide

This document assists consumers in migrating the `:about` module usage across versions.

## v0.x to v1.0.0
The `v1.0.0` release introduces a strict `AboutUiState` abstraction and removes the functional DSL builder. 

### Migrating to the Builder
**Before (v0.x - Experimental)**:
```kotlin
val config = rememberAboutConfig {
    appInfo { ... }
}
```

**After (v1.0.0)**:
```kotlin
val config = AboutConfig.Builder()
    .setAppInfo(AppInfo(...))
    .build()
```

### UI State
**Before (v0.x - Experimental)**:
```kotlin
AboutScreen(config = config)
```

**After (v1.0.0)**:
```kotlin
AboutScreen(
    uiState = AboutUiState.Success(config),
    eventListener = myListener
)
```
