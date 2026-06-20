# Architecture

The platform follows a clean architecture model:
- `SyncManager`: Facade for syncing, backup, and restore.
- `SyncProvider`: Cloud provider abstractions.
- `DisasterRecoveryEngine`: Rollback state.

Providers are injected at runtime to avoid vendor lock-in.
