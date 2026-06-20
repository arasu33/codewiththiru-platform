# Migration Guide to v1.0.0

1. Switch from `com.google.firebase:firebase-messaging` directly to `:notifications`.
2. Initialize `NotificationsManager` at app start.
3. Handle routing via deep links instead of custom FCM intents.
