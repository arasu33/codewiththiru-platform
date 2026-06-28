# Game Statistics Framework (`:game-statistics`)

A highly reusable, generic statistics and metrics tracking framework for all CodeWithThiru platform games.

## Features
- **Comprehensive Metrics Tracking**: Over 25 standard game metrics out-of-the-box (`GAMES_PLAYED`, `WIN_RATE`, `FASTEST_COMPLETION`, `STREAKS`, etc.).
- **Scoping**: Profiles available for `DAILY`, `WEEKLY`, `MONTHLY`, `YEARLY`, `LIFETIME`, and `CUSTOM` ranges.
- **Analytics Integration**: The `StatisticsTracker` translates local `GameEvent`s into metrics and cleanly separates analytics metadata without pulling in heavy SDKs (like Firebase) directly into this layer.
- **Advanced Maths**: `StatisticsCalculator` and `StatisticsAggregator` abstract out percentile calculations, median tracking, and trend analysis (e.g. "Is the player improving?").
- **Exporters**: Support for exporting stat sheets into CSV or JSON cleanly.
- **Leaderboards**: Generic interfaces for mapping local stats to global cloud leaderboards.

## Integration
This module is built using pure Kotlin and interfaces. The Android `app` module must provide standard dependencies and hook the `StatisticsTracker` to the `GameManager` event bus.

```kotlin
// Example Event Hookup
gameManager.eventFlow.collect { event -> 
    statisticsTracker.onGameEvent(event) 
}
```

## Testing
Run unit tests to verify the accuracy of the mathematical aggregators and metric tracking:
```bash
./gradlew :game-statistics:test
```
