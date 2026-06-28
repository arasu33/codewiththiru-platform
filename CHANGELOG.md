# Changelog

All notable changes to the CodeWithThiru Platform SDK will be documented in this file.

## [1.0.0] - 2026-06-28

### Added
- **Core Platform:** Initial production release of the entire CodeWithThiru Platform SDK.
- **Developer Platform (`developer/`)**: Introduced test-utils, game-testing fixtures, benchmark helpers, and architectural inspection tools.
- **Event Bus (`game-events`)**: Replaced tightly-coupled singleton dispatching with reactive `SharedFlow` mechanisms.
- **Save Engine (`game-save`)**: Built-in encrypted storage and conflict resolution interfaces.
- **Statistics & Leaderboards (`game-statistics`, `game-leaderboard`)**: Generic configurable trackers for streaks, xp, time, and wins/losses.
- **Profile System (`game-profile`)**: Standardized representations for identities (Guest, Cloud, Local).
- **Rewards & Achievements (`game-rewards`, `game-achievements`)**: Configurable mechanics to drive engagement.
- **Sensory (`game-audio`, `game-haptics`)**: Device-level abstractions for feedback.
- **Sync Engine (`game-sync`)**: High-level provider interfaces (e.g. Firebase, REST) to serialize off-device.

### Removed
- Removed the legacy `GameManager` and `DefaultGameManager` from `:game-common`.
- Removed `GameEvent.kt` and `LeaderboardManager.kt` from `:game-common` in favor of `:game-events` and `:game-leaderboard` modules.
- Removed legacy `StatisticsManager.kt` from `:game-common`.
