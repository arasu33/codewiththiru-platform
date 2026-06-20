# Security Audit

- **Notification Validation**: Payloads mapped through `NotificationFactory` ensuring strict typing.
- **Deep Link Validation**: `DeepLinkValidator` enforces allowed schemes (`codewiththiru`, `https`, `http`).
- **Permissions**: Safe permission checks for Android 13+ `POST_NOTIFICATIONS`.

# Reliability Audit

- **Retry Policies**: Uses `WorkManager` for scheduled notifications with built-in retry and constraints support.
- **Offline Recovery**: Local notifications function entirely offline.
- **Scheduling Recovery**: Periodic work persists across device reboots via `WorkManager`.

# Accessibility Audit

- **TalkBack Support**: Notifications structured with distinct title and message strings avoiding ambiguous text. BigText styles used for longer messages.
- **Large Font**: Dynamic text scaling supported natively by `NotificationCompat`.

# Performance Audit

- **Battery Optimization**: Replaced AlarmManager with `WorkManager` for non-exact reminders to allow Android to batch wake-ups.
- **Startup Audit**: Notification channels are created asynchronously or only when needed, reducing initialization time on app launch.
