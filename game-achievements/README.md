# Game Achievements Framework (`:game-achievements`)

A robust, highly generic achievements engine designed to plug into any CodeWithThiru platform game.

## Features
- **Extensible Conditions**: Supports complex evaluations using `CompositeCondition` (AND/OR logic) and `StatisticCondition` to trigger off of data from `:game-statistics`.
- **Unlock Strategies**: Accommodates achievements that unlock automatically, manually (player must claim), or on a schedule.
- **Rich Progression Tracking**: Maintains accurate percentage tracking towards goals (`AchievementProgress`), with state observations via Kotlin Coroutine `StateFlow`.
- **Pre-configured Rewards API**: Ready to hook into future modules via `AchievementReward` (supporting Coins, XP, Stars, and Custom payloads).
- **Zero Game-Specific Code**: Completely agnostic of the underlying game mechanics.

## Architecture & Integration
The framework is built interface-first. 
- The **`AchievementEngine`** acts as the brain.
- The **`AchievementTracker`** acts as the nervous system, listening to `:game-statistics` shifts or `GameEvent`s.
- The **`AchievementManager`** provides the client API for UI rendering and reward claims.

To register an achievement in the app module:
```kotlin
val winCondition = StatisticCondition(StatisticsMetric.GAMES_WON, 10.0)
achievementEngine.registerAchievement("win_10_games", winCondition)
```

## Testing
Run unit tests to verify condition evaluations and composite rule logic:
```bash
./gradlew :game-achievements:test
```
