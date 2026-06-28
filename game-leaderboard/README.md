# Game Leaderboard Framework (`:game-leaderboard`)

A robust, offline-capable ranking engine designed to handle percentiles, sorting, and cloud caching for competitive gameplay without direct dependencies on Google Play Games or Firebase.

## Features
- **Provider Interfaces**: `LeaderboardProvider` abstracts away the backend payload (Firebase/Supabase), ensuring the presentation layer deals purely in `LeaderboardEntry` instances.
- **Ranking Engine**: The `RankingEngine` accurately sorts entries based on high/low scores and accurately computes percentiles (e.g. "You are in the Top 5% of players") dynamically.
- **Sync Integration**: Interfaces natively with `:game-sync` to ensure that if a user scores a Personal Best while on an airplane, the score is securely queued and eventually resolved against the master cloud state.

## Testing
Run unit tests to verify sorting and percentile calculations:
```bash
./gradlew :game-leaderboard:test
```
