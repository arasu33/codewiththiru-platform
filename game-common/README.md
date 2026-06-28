# Game Core Module (`:game-core`)

This module serves as the foundational game framework for the CodeWithThiru Platform. It is designed to be completely generic, Android-independent, and reusable across all future games (AquaSort, Sudoku, Chess, Number Match, etc.).

## Core Principles

- **Clean Architecture:** Strict separation of concerns, domain-first models.
- **Pure Kotlin:** No Android framework dependencies (`Context`, `Activity`, etc.) to ensure portability and easier testing.
- **Interface-First Design:** All sub-systems (Audio, Haptics, Navigation, Animation) expose clean interfaces. Platform implementations are provided via dependency injection (Hilt).
- **Generic by Design:** Zero game-specific logic (e.g., no Water Sort specific logic). Everything must be reusable.

## Architecture & Components

The framework provides the following key components:

### 1. Core Engine
- `GameManager`: Orchestrates the game lifecycle, handling state transitions and dispatching events.
- `GameTimer`: Generic timer interface (with `DefaultGameTimer` providing Coroutine-based implementation).
- `GameEvent`: Sealed hierarchy for all game events, inherently mapped to analytics events via `toAnalyticsEvent()`.

### 2. State & Save System
- `SaveManager` & `Storage`: Interfaces for managing game saves.
- `StateSerializer`: Contract for converting state to/from a serialized format (e.g., JSON using `kotlinx.serialization`).

### 3. Progress & Statistics
- `StatisticsManager`: Tracks play sessions, streaks, time-zone safe resets.
- `AchievementManager`: Handles progressive achievements and milestone tracking.
- `RewardEngine` & `ChallengeEngine`: Engines for processing challenges and distributing rewards.

### 4. Audio & Haptics
- `Audio`: Interface for playing sounds, loops, and stopping audio.
- `Haptics`: Interface for triggering physical feedback patterns.

### 5. Utilities
- `AnimationCoordinator`: Manages cross-component motion definitions.
- `GameNavigator`: Facilitates routing in a platform-agnostic way.

## Integration & Dependency Injection

The `:game-core` module is designed to be Hilt-ready. Consumers of this framework should:
1. Provide concrete implementations for platform-specific capabilities (e.g., `AnalyticsManager`, `AdsProviders`, Android implementations of `Audio` and `Haptics`).
2. Bind these implementations to the interfaces defined in this module.

## Testing

The module includes comprehensive testing utilities (`FakeGameTimer`, `FakeAnalyticsManager`) and extensive unit tests covering the game lifecycle, statistics, and save systems.

Run tests using:
```bash
./gradlew :game-core:test
```
