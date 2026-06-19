# Migration Guide

This document assists in upgrading client integrations from older custom implementations or third-party wrappers to the platform `core` library.

---

## Migrating to Platform Core v1.0.x

### 1. Logger Integration
*   Replace standard `System.out.println` or third-party Logger libraries with `CustLogger`.
*   Ensure that you initialize the logger at startup (e.g. in your main application class):
    ```kotlin
    CustLogger.initialize(
        config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.INFO),
        printers = listOf(ConsoleLogPrinter())
    )
    ```

### 2. Result Wrap Migrations
*   Replace `kotlin.Result` with `CustResult`.
*   Replace `runCatching` blocks with `custRunCatching`. Note that `custRunCatching` preserves coroutines cancellation boundaries by rethrowing `CancellationException`.

### 3. Coroutines Dispatchers Injection
*   Refactor classes referencing `Dispatchers.IO`, `Dispatchers.Main` directly to accept `CustDispatcherProvider` in their constructors, easing unit-testing constraints.
