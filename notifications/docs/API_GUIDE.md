# API Guide for :notifications

## Initialization
```kotlin
val manager = DefaultNotificationManager(channelManager, provider)
manager.initialize()
```

## Showing Notifications
```kotlin
val payload = NotificationPayload(
    id = "123",
    title = "Hello",
    message = "World"
)
manager.showNotification(payload)
```
