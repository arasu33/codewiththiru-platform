# Game Settings Framework (`:game-settings`)

A robust, type-safe settings management engine designed to decouple UI preferences from raw storage strings.

## Features
- **Strong Typing**: `SettingKey` and `SettingValue` prevent accidental casts of boolean flags into strings or integers.
- **Validation Engine**: `SettingValidator` ensures values never exceed boundaries (e.g. `IntRangeValidator` for Volume 0-100).
- **Migration Pipeline**: `SettingMigration` safely maps deprecated keys to new values. If `music_volume` (0-100) is deprecated for a simple `is_music_enabled` (boolean), the migration block handles it transparently.
- **Backup & Snapshotting**: Generates a `SettingsSnapshot` that can be serialized and exported/imported effortlessly.

## Testing
Run unit tests to verify migrations and bounds-checking:
```bash
./gradlew :game-settings:test
```
