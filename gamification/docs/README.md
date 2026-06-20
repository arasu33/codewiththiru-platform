# CodeWithThiru Gamification Platform

Welcome to the `:gamification` module! This is an offline-first, provider-agnostic, enterprise-grade gamification engine.

## Features Included (Version 1.0.0)
1. **XP & Leveling System**
2. **Achievements Platform**
3. **Badges & Collections**
4. **Streak Engine**
5. **Challenges & Quests**
6. **Reward Economy (Coins & Gems)**
7. **Leaderboards**
8. **Seasons & Live Events**
9. **Analytics & Engagement**
10. **Security & Anti-Cheat Engine**

## Architecture

This SDK strictly follows Clean Architecture:
* `api/`: Public interfaces (Manager, Models).
* `repository/`: Local DataStore-backed state repository for Offline-First capability.
* Domain packages (`xp/`, `achievement/`, `streak/`, `challenge/`, `economy/`): Business logic engines.
* `anticheat/`: Security validators for detecting offline tampering.

## Integration
```kotlin
implementation(projects.gamification)
```
See `API_GUIDE.md` for detailed instructions.
