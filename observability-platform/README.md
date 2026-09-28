# 📡 Observability Platform (`:observability-platform`)

The central nervous system for monitoring, diagnostics, crash reporting, performance tracing, and telemetry across the platform.

---

## 🚀 Features

- **Structured Logging**: Unified JSON/structured logging interface with privacy-aware PII scrubbing.
- **Crash Reporting (`CrashReporter`)**: Deduplicated exception grouping with breadcrumbs and fatal/non-fatal separation.
- **Performance Tracing (`PerformanceMonitor`)**: Measure app startup times, frame render latencies, and network round-trips.
- **ANR & Memory Detection**: Automated slow-thread detection and memory leak alerts.
- **Health Checks (`HealthMonitor`)**: Real-time health diagnostic probes for database, network, and storage subsystems.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:observability-platform")
}
```

---

## 📚 Documentation
For complete architectural details and integration recipes, see [docs/README.md](docs/README.md).
