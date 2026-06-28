# Game Save Framework (`:game-save`)

This module provides a production-grade, generic Save Framework for the CodeWithThiru Platform. It is built to support saving, loading, backing up, and migrating state for any game built on the platform.

## Features

- **Core Save Management**: Supports manual saves, autosaves, quick saves, and suspend states through `GameSaveManager`.
- **Serialization**: Abstractions for JSON, CBOR, or binary serialization via the `Serializer` interface.
- **Security & Integrity**: Integrates with the platform's security policies to support `EncryptionStrategy` and `ChecksumGenerator` to prevent save tampering.
- **Backups & Restoration**: Interfaces for local and cloud backups via `BackupManager` and `CloudSyncStrategy`.
- **Migration**: Forward and backward compatibility handlers via `MigrationManager`.
- **Android Independent**: Pure Kotlin implementation built strictly with interfaces, enabling testing and swapping of persistence layers (e.g. SQLite, DataStore) without changing the core framework.

## Usage

Consumers must provide implementations for the abstract strategies (e.g., File I/O, encryption, and cloud sync) and inject them using Hilt.

### Testing

This module includes `FakeGameSaveManager` and other fakes for easy unit testing in consumer modules.

Run tests:
```bash
./gradlew :game-save:test
```
