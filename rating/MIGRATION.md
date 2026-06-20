# Migration Guide

## Upgrading to 0.1.0

This is the initial release of the `:rating` module. There are no legacy APIs to migrate from.

### Initial Setup Checks
- Ensure your host application provides a valid `PlayReviewProvider` implementation hooking into Google Play Core.
- If you were previously using custom SharedPreferences for rating counts, the new `:rating` module uses `androidx.datastore:datastore-preferences`. The old preferences will naturally become obsolete unless you write a custom one-time migration script into the `RatingStorageProvider`.
