# CodeWithThiru Platform Core Library

The `core` module is a pure Kotlin JVM library providing foundational operational utilities for the CodeWithThiru platform. It enforces clean architecture, SOLID principles, and zero Android SDK dependencies.

## Key Features

1.  **Logger (`com.codewiththiru.platform.core.logger`)**: A pluggable, thread-safe, lock-free logging system with log-level filtering support.
2.  **Result Wrapper (`com.codewiththiru.platform.core.result`)**: A sealed interface wrapper for functional-style operations (`map`, `flatMap`, `fold`, `recover`, etc.) with Kotlin compiler contract compliance.
3.  **Dispatcher Provider (`com.codewiththiru.platform.core.dispatcher`)**: Swappable thread scheduler mappings promoting clean coroutines unit-testability.
4.  **Date Utilities (`com.codewiththiru.platform.core.date`)**: Modern Java 8 Time-based formatting, parsing, and extensible timezone-aware relative time calculation.
5.  **String Utilities (`com.codewiththiru.platform.core.string`)**: Focused, lightweight RFC email validation, E.164 phone validation, URL encoding/decoding, and HTML entity escaping/tag stripping.

---

## Documentation Index

For detailed guides, please refer to:
*   [API Guide](API_GUIDE.md): Code examples demonstrating how to use Logger, Result, and Dispatchers.
*   [Migration Guide](MIGRATION.md): Upgrade guidelines for integrations.
*   [Changelog](CHANGELOG.md): Complete release history.

---

## Installation

Add the library dependency to your module's `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.codewiththiru.platform:core:1.0.0-SNAPSHOT")
}
```
