# CodeWithThiru Platform SDK

Welcome to the **CodeWithThiru Platform**, the definitive foundation for all future game titles (AquaSort, Sudoku, Chess, 2048, etc.).

## Version: v1.0.0

This SDK provides production-grade, highly-testable, and decoupled modules for:
- **Core Engineering**: Dependency Injection (Hilt), Coroutines, Observability
- **Game Engine**: Reactive State Management (`:game-events`), Sync (`:game-sync`), Profiles (`:game-profile`)
- **Game Mechanics**: Achievements (`:game-achievements`), Rewards (`:game-rewards`), Statistics (`:game-statistics`), Leaderboards (`:game-leaderboard`)
- **Developer Experience**: Fixture Generators (`:developer:game-testing`), Time/Clock Mocks (`:developer:test-utils`)

## Getting Started

In your application's `build.gradle.kts`:
```kotlin
dependencies {
    // Import the BOM for standardized dependency versions across all platform modules
    implementation(platform("com.codewiththiru.platform:platform-bom:1.0.0"))
    
    // Choose the modules you need
    implementation("com.codewiththiru.platform:game-events")
    implementation("com.codewiththiru.platform:game-profile")
    implementation("com.codewiththiru.platform:game-sync")
}
```

## Architecture Principles
1. **Pure Kotlin**: No Android framework dependencies in the core domain layers.
2. **Event-Driven**: Modules communicate via the `:game-events` bus, never through tightly-coupled singletons.
3. **Thread-Safe**: All flows and state operations are designed for highly concurrent environments.

## Contribution Guidelines
Please read `CONTRIBUTING.md` (coming soon) before submitting any PRs. All code must pass the strict `detekt` and `ktlint` quality gates.
