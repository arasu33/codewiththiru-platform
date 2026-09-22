# CodeWithThiru Platform SDK

Welcome to the **CodeWithThiru Platform**, the definitive foundation for all future game titles (AquaSort, Sudoku, Chess, 2048, etc.).

## Version: v1.4.1

This SDK provides production-grade, highly-testable, and decoupled modules for:
- **Core Engineering**: Dependency Injection (Hilt), Coroutines, Observability
- **Game Engine**: Reactive State Management (`:game-events`), Sync (`:game-sync`), Profiles (`:game-profile`)
- **Game Mechanics**: Achievements (`:game-achievements`), Rewards (`:game-rewards`), Statistics (`:game-statistics`), Leaderboards (`:game-leaderboard`)
- **Developer Experience**: Fixture Generators (`:developer:game-testing`), Time/Clock Mocks (`:developer:test-utils`)

## Installation & Publishing (GitHub Packages)

To consume this SDK in your application (e.g., AquaSort), you must authenticate with the GitHub Packages registry.

1. **Configure your `settings.gradle.kts`:**
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/arasu33/codewiththiru-platform")
            credentials {
                username = System.getenv("GPR_USER") ?: "your_github_username"
                password = System.getenv("GPR_KEY") ?: "your_personal_access_token"
            }
        }
    }
}
```

2. **Add the BOM to your `build.gradle.kts`:**
```kotlin
dependencies {
    // Import the BOM for standardized dependency versions across all platform modules
    implementation(platform("com.codewiththiru.platform:platform-bom:1.4.1"))
    
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
