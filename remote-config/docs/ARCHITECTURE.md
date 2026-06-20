# Architecture Overview

## Clean Architecture Principles

The Remote Config module follows clean architecture:
- **Core Domain:** `RemoteConfigKey`, `FeatureFlag`, `Experiment` are domain models independent of implementation details.
- **Provider Pattern:** `RemoteConfigProvider` abstraction hides Firebase and JSON specific libraries. The rest of the app never imports `com.google.firebase`.
- **Repository Pattern:** `DefaultRemoteConfigRepository` acts as the single source of truth and orchestrator for caching, validation, and analytics.

## Strategy / Hierarchy

```mermaid
graph TD
    Client[Client Module] --> Repo[RemoteConfigRepository]
    Repo --> KS[KillSwitchManager]
    KS --> |Bypass if Killed| Client
    KS --> |Allow| Mem[Memory Cache]
    Mem --> |Hit| Repo
    Mem --> |Miss| DS[DataStore Cache]
    DS --> |Hit| Mem
    DS --> |Miss| FB[Firebase Provider]
    FB --> |Success| DS
    FB --> |Fail| JSON[Local JSON Fallback]
    JSON --> |Success| DS
    JSON --> |Fail| Def[Hardcoded Defaults]
```

## Refresh Engine
The default refresh engine utilizes a 6-hour TTL (`REFRESH_INTERVAL_MS`).
A `Mutex` ensures that concurrent requests for `refresh()` from multiple parts of the app during startup collapse into a single network call.
