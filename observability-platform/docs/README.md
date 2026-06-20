# CodeWithThiru Observability Platform

Welcome to the `:observability-platform` module! This module is the central nervous system for monitoring, diagnostics, and telemetry across the entire CodeWithThiru ecosystem.

## Features Included (Version 1.0.0)
1. **Foundation**: Offline-ready architecture (`ObservabilityManager`)
2. **Structured Logging**: Unified JSON/structured logging interface (`Logger`, `LogFormatter`)
3. **Crash Reporting**: Deduplicated exception grouping and fatal/non-fatal separation (`CrashReporter`)
4. **Performance Monitoring**: Real-time startup metrics and UI frame drops (`PerformanceMonitor`)
5. **Memory & ANR**: Memory snapshots, OOM warnings, and block detection (`MemoryLeakDetector`, `ANRMonitor`)
6. **Network & API**: Detailed request/response telemetry (`NetworkMonitor`)
7. **Tracing & Telemetry**: Distributed cross-module traces (`TelemetryManager`)
8. **Health Monitoring**: Component health checks (`HealthMonitor`)
9. **Alerting & Incident**: Automated threshold alerts (`AlertManager`, `IncidentManager`)
10. **Dashboards**: Configurable multi-persona reporting metrics (`DashboardProvider`)

## Integration
```kotlin
implementation(projects.observabilityPlatform)
```
*Note: Make sure your `settings.gradle.kts` uses `:observability-platform`.*
