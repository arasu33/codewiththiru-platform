# Game Challenges Framework (`:game-challenges`)

A pure Kotlin framework orchestrating complex objective loops, including Daily/Weekly/Monthly cycles, condition evaluation, and streak engines.

## Features
- **Stat-Driven Evaluation**: Rather than game-specific logic, the `ChallengeEngine` ingests raw numeric updates (e.g. `trackStatistic("games_won", 1)`) and evaluates them against `ChallengeCondition` lists.
- **Robust Scheduling**: `ChallengeScheduler` handles time math entirely without Android, verifying expiration windows, grace periods, and cooling downs natively on the JVM.
- **Streak Preservation**: Built-in support for consecutive-day completion tracking, factoring in "freeze tokens" for missed days.
- **Reward Engine Integration**: Tightly coupled (via interfaces) with `:game-rewards` to immediately issue payloads when a challenge crosses into `ChallengeStatus.COMPLETED`.

## Testing
Run unit tests to verify condition evaluations and progression math:
```bash
./gradlew :game-challenges:test
```
