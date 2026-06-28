# Game Haptics Framework (`:game-haptics`)

A purely abstract, highly configurable Kotlin framework for orchestrating haptic feedback, vibrations, and tactile responses across CodeWithThiru platform games.

## Features
- **Hardware Agnostic**: Relies on a `HapticProvider` interface. The Android application module is responsible for binding the `Vibrator` service, keeping this domain pure.
- **Capabilities Detection**: Safely checks `HapticCapabilities` (e.g., `hasAmplitudeControl()`) before attempting rich vibrations.
- **Dynamic Amplitude Scaling**: Patterns can be scaled mathematically (e.g., `intensityScale = 0.5f`) for battery saver modes or accessibility preferences.
- **Policy Suppression**: Easily mute haptic responses globally via `HapticConfiguration` or `HapticPolicy`.

## Integration
This module provides the orchestrators. The application (or platform layer) must inject the concrete engines.

```kotlin
// Example: The platform provides a concrete HapticProvider
val androidVibrator: HapticProvider = MyAndroidVibratorWrapper(context)

// The Game logic interacts exclusively with the abstract Managers
hapticManager.performHapticFeedback(HapticPatternType.SUCCESS)
```

## Testing
Run unit tests to verify waveform math and fallback protocols:
```bash
./gradlew :game-haptics:test
```
