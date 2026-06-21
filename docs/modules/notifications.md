# Notifications Module

## Purpose
The `notifications` module handles both remote push notifications via Firebase Cloud Messaging (FCM) and locally scheduled notifications using Android WorkManager. It ensures high engagement while respecting user preferences and OS restrictions.

## Architecture
- **FCM Receiver**: A service extending `FirebaseMessagingService` to intercept remote pushes.
- **Notification Builder**: A centralized factory for constructing consistent `NotificationCompat` objects.
- **WorkManager**: Background workers for delayed or recurring local notifications (e.g., reminders).
- **Channel Manager**: Registers Notification Channels required for Android 8.0+.

## Public APIs
- `PlatformMessagingService`: The FCM entry point.
- `NotificationScheduler`: API to schedule, cancel, or update local notifications.
- `PushTokenManager`: Manages FCM token retrieval and backend synchronization.

## Configuration
Define notification channels, priority levels, colors, and default icons in a centralized configuration object.

## Dependencies
```gradle
implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")
implementation("androidx.work:work-runtime-ktx:2.9.0")
```

## Initialization
Create notification channels on app startup. FCM token retrieval should happen silently.
```kotlin
FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
    if (task.isSuccessful) {
        val token = task.result
        // Sync token with backend
    }
}
```

## Integration Steps
1. Add FCM to `google-services.json`.
2. Register `PlatformMessagingService` in the Manifest.
3. Call `NotificationChannelManager.init()` in `Application.onCreate()`.
4. Prompt the user for the `POST_NOTIFICATIONS` permission (Android 13+).

## Required Permissions
- `android.permission.POST_NOTIFICATIONS` (Android 13+)
- `android.permission.INTERNET`
- `android.permission.VIBRATE`
- `android.permission.WAKE_LOCK`
- `android.permission.RECEIVE_BOOT_COMPLETED` (For restoring WorkManager alarms)

## Manifest Entries
```xml
<service
    android:name=".PlatformMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
<meta-data
    android:name="com.google.firebase.messaging.default_notification_icon"
    android:resource="@drawable/ic_notification" />
```

## Remote Config & Analytics Dependencies
- **Remote Config**: Enable/disable specific push campaigns.
- **Analytics**: Logs `notification_received`, `notification_opened`, and `notification_dismissed`.

## Security
Do not send sensitive PII inside the FCM payload, as pushes can be intercepted or read on the lock screen. Fetch sensitive data dynamically upon app open.

## Accessibility
Use distinct sound and vibration patterns for different channels. Ensure intent actions are easily reachable.

## Testing
- Send test pushes via the Firebase Console.
- Test local workers using `WorkManagerTestInitHelper`.

## Migration
Migrating from AlarmManager to WorkManager requires clearing legacy alarms and rescheduling via `PeriodicWorkRequest`.

## Troubleshooting
- **Not receiving pushes in background**: Check OEM-specific battery optimization settings. Ensure the app is not force-stopped.
- **Permission denied**: Always handle the rationale flow for Android 13+ `POST_NOTIFICATIONS`.

## Examples
```kotlin
fun scheduleReminder(context: Context, timeInMillis: Long) {
    val request = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        .build()
    WorkManager.getInstance(context).enqueue(request)
}
```
