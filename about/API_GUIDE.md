# API Guide: About Module

## Core Components
### `AboutConfig`
Constructed using the `Builder` pattern. Handles the structural presentation models.
```kotlin
val config = AboutConfig.Builder()
    .setAppInfo(AppInfo(...))
    .setDeveloperInfo(DeveloperInfo(...))
    .build()
```

### `AboutUiState`
Wrap the configuration in a semantic state.
```kotlin
val state = AboutUiState.Success(config)
```

### `AboutScreen`
The single UI entry point. It enforces the `:designsystem` UI token constraints natively.
```kotlin
AboutScreen(
    uiState = state,
    eventListener = object : AboutEventListener {
        // Implement callbacks for sharing, copying diagnostics, etc.
    },
    licenseProvider = MyLicenseProvider() // Optional OSS abstraction
)
```

## Providers
Implement `CustDeviceInfoProvider` and `CustBuildInfoProvider` internally to pipe app/os details securely from your `core` application scope down into the `:about` builder constraints.
