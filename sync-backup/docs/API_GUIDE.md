# API Guide

```kotlin
// Initialize
syncManager.initialize(SyncConfig())

// Trigger Sync
val result = syncManager.syncNow()

// Trigger Backup
val backupResult = syncManager.requestBackup()
```
