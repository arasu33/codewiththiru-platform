# Game Audio Framework (`:game-audio`)

A pure Kotlin, platform-agnostic audio orchestration framework for CodeWithThiru platform games.

## Features
- **Hardware Agnostic**: Relies on an `AudioPlayer` interface. The application module must provide the actual ExoPlayer/MediaPlayer implementation, keeping the domain logic pure and highly testable.
- **Complex Hierarchies**: Supports multiple volume axes (`masterVolume`, `musicVolume`, `effectsVolume`) mathematically combined in real-time.
- **Queueing & Caching**: Defines `AudioQueue` for sequential playback logic and `AudioCache` to ensure sound effects fire instantly with zero latency.
- **Lifecycle & Focus**: `AudioSession` integrates with OS lifecycle hooks to duck or pause audio appropriately when the app backgrounds or loses focus.

## Integration
This module provides the orchestrators. The application (or platform layer) must inject the concrete engines.

```kotlin
// Example: The platform provides a concrete AudioPlayer
val exoPlayerWrapper: AudioPlayer = MyAndroidExoPlayerImplementation()

// The Game logic interacts exclusively with the abstract Managers
musicManager.playMusic("level_1_bgm", loop = true, fadeMs = 2000)
```

## Testing
Run unit tests to verify volume multiplication logic and configuration integrity:
```bash
./gradlew :game-audio:test
```
