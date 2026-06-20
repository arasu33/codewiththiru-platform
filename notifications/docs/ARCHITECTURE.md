# Architecture

- **Clean Architecture**: Use case driven approach.
- **Provider Agnostic**: Push and Local providers abstract away FCM and Android `NotificationManagerCompat`.
- **MVI State Flow**: State is managed via `StateFlow` in `NotificationManager`.
