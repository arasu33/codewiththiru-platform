# Migration Guide: Pre-v1.0 to v1.0.0

This guide outlines breaking changes and migration paths for games upgrading to the `1.0.0` stable SDK.

## 1. GameEvent Refactor
The legacy `GameEvent` sealed class located in `:game-common` has been **deleted**.
All references to `GameEvent` must now be replaced with `PlatformEvent` or the new `GameEvent` hierarchy located in the `:game-events` module.

**Before:**
```kotlin
import com.codewiththiru.platform.game.events.GameEvent
// using event inside :game-common
```

**After:**
```kotlin
// Ensure you have `implementation(project(":game-events"))` in your build.gradle.kts
import com.codewiththiru.platform.game.events.GameEvent
import com.codewiththiru.platform.game.events.api.PlatformEvent
```

## 2. Deprecation of GameManager
The monolithic `GameManager` and `DefaultGameManager` in `:game-common` have been deleted. Game-specific logic should no longer be shoved into a single interface. Instead, rely on the localized managers (e.g. `ProfileManager`, `StatisticsManager`) and communicate over the `GameEventBus`.

## 3. Dependency Updates
Ensure you are using the unified platform-bom:
```kotlin
implementation(platform("com.codewiththiru.platform:platform-bom:1.0.0"))
```
Remove all explicit `-SNAPSHOT` versions in your build scripts.
