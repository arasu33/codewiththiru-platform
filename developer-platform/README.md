# 🛠️ Developer Platform (`:developer-platform`)

Developer productivity utilities, diagnostic tooling, feature flag debug panels, and developer benchmarking harnesses.

---

## 🚀 Features

- **Debug Inspector**: Inspect in-memory state, active coroutines, network logs, and pending storage mutations.
- **Feature Flag Overrides**: Runtime UI to force enable/disable feature flags and remote configs locally.
- **Leak & Performance Benchmarking**: Integration with Android Benchmark and automated GC profile trackers.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    // Typically integrated in debug builds only
    debugImplementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    debugImplementation("com.codewiththiru.platform:developer-platform")
}
```
