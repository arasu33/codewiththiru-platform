# Cache Architecture

## Overview
The Remote Config Cache system provides a multi-layer (L1 Memory, L2 DataStore) storage mechanism to ensure offline availability and high performance.

## Layers
1. **L1: Memory Cache (`MemoryRemoteConfigCache`)**: Instant reads. Populated on app launch from L2.
2. **L2: DataStore Cache (`DataStoreRemoteConfigCache`)**: Persistent storage. Populated when provider fetches succeed.

## Versioning & Migration
* `CachedRemoteConfig`: Data class wrapping the raw payload with a `schemaVersion` and `lastUpdated` timestamp.
* `CacheMigrationManager`: Executes schema migrations linearly through `CacheMigration` steps registered in `CacheVersionRegistry`.
* **Downgrades**: Unsupported. If a downgrade is detected, the cache is wiped and treated as corrupted.

## Policies & Metrics
* `CachePolicy`: Configures max age (default 6 hours) and whether layers are enabled.
* `CacheMetrics`: Tracks hit/miss ratio, evictions, migrations, and corruption counts to feed into `HealthMonitor`.
