# 🔄 Sync & Backup Platform (`:sync-backup`)

Enterprise Sync, Backup & Cross-Device Platform for Android applications.

## Features
- **Offline-First Architecture**: Seamless local caching with background synchronization.
- **Cloud Sync**: Incremental & delta synchronization reducing network usage.
- **Cross-Device Conflict Resolution**: Configurable strategies (`ServerWins`, `ClientWins`, `LatestTimestamp`, `Custom`).
- **End-to-End Encrypted Backups**: AES-GCM encrypted backup payloads ensuring user data privacy.
- **Disaster Recovery**: Automated snapshot restore and state repair.

## Quick Start

### 1. Add Dependencies
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:sync-backup")
}
```

### 2. Configure Sync Engine
```kotlin
val syncConfig = SyncConfig(
    autoSyncIntervalMinutes = 15,
    retryPolicy = ExponentialBackoffPolicy(maxRetries = 3),
    conflictResolver = ConflictResolutionStrategy.LATEST_TIMESTAMP
)

val syncManager: SyncManager = DefaultSyncManager(
    context = context,
    config = syncConfig
)

// Trigger on-demand sync
syncManager.syncNow()
```
