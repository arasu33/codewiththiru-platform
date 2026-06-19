# Architecture Decisions

This document outlines the core architectural decisions made for the `codewiththiru-platform` project and the rationale behind them.

## 1. Why `core` is JVM Only
The `:core` module contains foundational logic, utility abstractions, and domain models. Keeping it purely JVM-based ensures:
- **Fast Execution**: Unit tests run instantly on the local JVM without the overhead of Robolectric or an Android emulator.
- **Strict Separation of Concerns**: It prevents accidental leaking of Android Framework dependencies into business logic.
- **Portability**: Code remains highly portable and framework-agnostic.

## 2. Why `core-android` Exists
While the `:core` module defines the interfaces, `:core-android` provides the platform-specific implementations that require the Android Framework (e.g., `Context`, `PackageManager`, `Build`). This allows us to inject Android-aware implementations into abstract interfaces defined in `:core`, strictly preserving the Dependency Inversion principle.

## 3. Why Logger Uses Printers
The logging infrastructure (`CustLogger`) uses pluggable `CustLogPrinter` interfaces because:
- **Flexibility**: We can route logs to multiple destinations simultaneously (e.g., standard console, Crashlytics, local files) without changing the core logging API.
- **Separation of Concerns**: It cleanly separates the formatting and filtering logic from the log destination logic.

## 4. Why `CustResult` Exists
Using `CustResult` enforces functional, type-safe error handling. Instead of relying on unpredictable runtime exceptions that can crash the app, `CustResult` explicitly models both `Success` and `Failure` states, forcing the consumer to handle errors gracefully through ergonomic functions like `fold()`, `map()`, and `onFailure()`.

## 5. Why `java.time` was Selected
The `java.time` API (introduced in Java 8) is modern, immutable, and inherently thread-safe. It effectively replaces the mutable and notoriously error-prone legacy `java.util.Date` and `java.util.Calendar` APIs. Thanks to Android's API desugaring, `java.time` can be utilized fully across all target SDK versions.

## 6. Why Interfaces are Used
An interface-first design approach is mandated across all platform services because it:
- **Promotes Testability**: Consumers can easily substitute real dependencies with MockK or test fakes.
- **Enforces Contracts**: The consumer depends on *what* a component does, not *how* it does it, naturally hiding implementation details.

## 7. Why `Context` is Constructor Injected
We explicitly avoid Singletons and static references to Android `Context`. Constructor injection:
- **Prevents Memory Leaks**: It ties the lifecycle of the utility explicitly to the scope of the provided `Context` (e.g., Application vs Activity).
- **Improves Testability**: Dependencies can be effortlessly instantiated and tested in isolation by passing a mocked or `Robolectric` context.
