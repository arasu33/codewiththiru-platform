# Gamification Module

## Purpose
The `gamification` module drives user engagement by introducing game-like mechanics into the CodeWithThiru platform. It tracks user progress, calculates streaks, awards badges, and manages a points-based economy.

## Architecture
- **Rules Engine**: Evaluates user actions against a set of predefined conditions to award points.
- **Room Database**: Caches current score, badges, and history locally for offline support.
- **Sync Manager**: Syncs local gamification events with the remote backend when the network is available.
- **UI Components**: Provides reusable Compose widgets for Level Progress, Badge Grids, and Streak counters.

## Public APIs
- `GamificationEngine`: Central hub to log actions (e.g., `logAction(ActionType.COMPLETED_LESSON)`).
- `GamificationRepository`: Exposes `Flow<UserProfile>` containing current points and rank.
- `LeaderboardManager`: Fetches global or friend-based leaderboards.

## Configuration
Action point values and level thresholds are defined in a JSON config or fetched via Remote Config to allow dynamic balancing.

## Dependencies
```gradle
implementation("androidx.room:room-ktx:2.6.1")
implementation("androidx.room:room-runtime:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")
```

## Initialization
Room database initializes lazily. The Sync Manager should be triggered on app startup or via WorkManager.

## Integration Steps
1. Add the Room compiler to your KSP configurations.
2. Inject `GamificationEngine` into ViewModels where user actions occur.
3. Call `logAction()` when significant events happen (e.g., finishing a module, daily login).

## Required Permissions
- None specifically required (relies on underlying network modules for sync).

## Manifest Entries
None required.

## Remote Config & Analytics Dependencies
- **Remote Config**: Used to host live-ops events (e.g., "Double Points Weekend").
- **Analytics**: Log `badge_earned`, `level_up`, and `streak_lost` events.

## Security
- Implement anti-cheat mechanisms on the backend. The client should never directly dictate its point total; it should only submit signed action events for validation.
- Rate-limit action submissions to prevent API abuse.

## Accessibility
- Ensure "Level Up" animations do not trigger photosensitivity issues (respect `AnimatorDurationScale`).
- Provide content descriptions for badges (e.g., "Gold Contributor Badge: Earned on May 1st").

## Testing
- Use fake clocks in tests to simulate streak continuations or streak breaks across different days.
- Mock the Room DB to test level threshold boundary conditions.

## Migration
Database schema migrations must be handled carefully using Room's `Migration` classes to avoid losing user progress data locally before a sync.

## Troubleshooting
- **Sync Conflicts**: If the offline DB and the backend mismatch, the backend is the source of truth. Resolve conflicts by overwriting local state.
- **Streaks resetting early**: Ensure timezone logic is consistent. Streaks should generally be calculated based on the user's local timezone.

## Examples
```kotlin
viewModelScope.launch {
    val result = gamificationEngine.logAction(Action.DAILY_LOGIN)
    if (result.leveledUp) {
        _uiEvent.emit(UiEvent.ShowLevelUpDialog(result.newLevel))
    }
}
```
