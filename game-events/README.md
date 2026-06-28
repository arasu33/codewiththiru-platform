# Game Events Framework (`:game-events`)

A robust, reactive event bus powered by Kotlin Coroutines (`SharedFlow`). This module acts as the decoupling backbone of the platform, allowing features (like analytics, audio, save triggers) to respond to state changes without tightly coupling their SDKs to the caller.

## Features
- **Coroutine Reactive Bus**: Replaces legacy interface callbacks with highly concurrent `GameEventBus` powered by `MutableSharedFlow`.
- **Event Filtering**: Implements `EventFilter` which allows subscribers to drop irrelevant spam locally before it executes their listener logic.
- **Ring Buffer History**: The `EventHistory` queue automatically bounds crash-reporters to the last N events.
- **Analytics Ready**: Every `PlatformEvent` enforces `toAnalyticsEvent()` to ensure tracking payloads are identical across platforms.

## Testing
Run unit tests to verify flow routing and ring-buffer constraints:
```bash
./gradlew :game-events:test
```
