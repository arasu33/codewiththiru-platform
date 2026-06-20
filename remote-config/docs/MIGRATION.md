# Migration Guide

## Upgrading to 1.0.0

The old `RemoteConfigRepository` has been deprecated and replaced by the new `RemoteConfigManager` facade.

### Removed APIs
- `fetch()` - Replaced by `refresh()` and `forceRefresh()`.
- Direct Firebase API access. Use `FirebaseRemoteConfigProvider` injected into the `CompositeRemoteConfigProvider`.

### Cache Upgrades
- Legacy preferences cache will be automatically wiped by `CacheMigrationManager` on first launch. Subsequent updates will leverage the new `schemaVersion` system in `CachedRemoteConfig`.
