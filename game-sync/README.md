# Game Sync Framework (`:game-sync`)

A provider-agnostic, offline-first synchronization engine designed to act as the traffic controller between local state (`:game-save`, `:game-profile`) and any cloud provider.

## Features
- **Provider Interfaces**: `SyncProvider` allows wrapping any backend (Firebase Firestore, Supabase, internal REST API) without poisoning the business logic with vendor SDKs.
- **Queueing Engine**: Uses `SyncQueueEngine` to track atomic `SyncRequest` mutations. If a user completes a challenge while offline, the payload goes to the queue and waits for `NetworkMonitor` to detect connectivity.
- **Conflict Resolution**: `ConflictResolver` ensures deterministic merging. By default, `NewestWinsResolver` ensures that multi-device setups gracefully overwrite outdated sync states based on timestamp comparisons.

## Testing
Run unit tests to verify conflict resolution algorithms and queue retry capabilities:
```bash
./gradlew :game-sync:test
```
