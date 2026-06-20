# Enterprise Remote Config Module

The Remote Config Module provides a robust, multi-layer configuration engine for the CodeWithThiru platform. It enables dynamic updates, A/B testing, feature flagging, and centralized configuration management without needing app updates.

## Key Capabilities

- **Multi-layered Fetch Strategy:** Local Defaults → DataStore Cache → Memory Cache → Firebase Provider.
- **Offline First:** Loads from DataStore or local JSON assets when offline.
- **Robust Security:** Payload schema validation, SHA256 integrity checks, and kill switch enforcement.
- **Feature Flag System:** Rule-based feature rollout, segmentation, and overrides.
- **Experimentation Engine:** Sticky assignment, percentage rollout, and control groups.
- **Zero Main-Thread Blocking:** Fully Coroutines/Flow based.

## Quick Start

```kotlin
val remoteConfigRepo: RemoteConfigRepository = ...

// Fetching a typed value
val isAdsEnabled = remoteConfigRepo.getValue(RemoteConfigKey.AdsEnabled)

// Reacting to changes
lifecycleScope.launch {
    remoteConfigRepo.state.collect { state ->
        when (state) {
            is RemoteConfigState.Success -> { /* handle ready */ }
            is RemoteConfigState.Error -> { /* handle error */ }
            else -> {}
        }
    }
}
```

## Structure
- `api/` - Strongly typed config keys.
- `provider/` - Firebase, JSON and multi-provider sources.
- `cache/` - Memory and DataStore L1/L2 caches.
- `featureflags/` - Core logic for boolean flag gating.
- `experiment/` - Variant assignments and hashing.
- `security/` - Payload validation, timestamps, kill switches.
- `repository/` - Central engine coordinating components.

Please see the other docs in this folder for more details.
