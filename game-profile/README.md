# Game Profile Framework (`:game-profile`)

A pure Kotlin central hub for managing player identity, customization inventories, and experience level progression across all CodeWithThiru platform games.

## Features
- **Identity Agnostic**: Connects anonymous local guests or fully authenticated cloud users (Google Play Games/Firebase) beneath a common abstraction `PlayerIdentity`.
- **Dynamic XP Engine**: The `ExperienceEngine` cleanly processes raw XP gains, mathematically calculating level jumps, remainder overflow XP, and prestige ranks independently of the UI.
- **Customization Inventory**: `PlayerCustomization` tracks unlocked cosmetic items (Avatars, Frames, Titles, Badges, Themes) and persists the user's active selections.
- **Central Preferences**: Provides standard toggles (`ProfilePreferences`) for Audio, Haptics, and Accessibility, accessible globally.

## Testing
Run unit tests to verify XP mathematical algorithms:
```bash
./gradlew :game-profile:test
```
