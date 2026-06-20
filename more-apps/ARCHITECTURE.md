# Architecture Overview

The `:more-apps` module strictly adheres to **Clean Architecture** principles and **MVVM** to decouple UI, business logic, and data sources.

## Core Principles
1. **DI Agnostic**: The SDK does not force any specific DI framework (Hilt/Koin). Consumers wire dependencies manually or via their own DI graph.
2. **Abstract Dependencies**: We use providers for anything that might force a transitive dependency:
    - `MoreAppsImageProvider` instead of Coil.
    - `CacheProvider` instead of Room.
    - `MoreAppsAnalyticsProvider` instead of Firebase.
3. **MVI/MVVM State**: The UI reacts entirely to `MoreAppsUiState` and consumes one-time events via `MoreAppsEffect`.

## Layers
* **Domain Layer**: Contains `MoreAppModel`, `InstallStatus`, `MoreAppsResult`. It holds the core abstractions (`MoreAppsRepository`, `MoreAppsClock`).
* **Data Layer**: Connects data sources (`LocalMoreAppsDataSource`, `RemoteMoreAppsDataSource`) to the repository and integrates caching logic.
* **Presentation Layer**: Exclusively Jetpack Compose. Configured via `MoreAppsConfig`.

## Accessibility (a11y)
The UI is built with merged semantic nodes. App cards announce their entire state (e.g. "App Name, Rating 4.8, Installed") at once for better TalkBack UX.
