# Platform Layers

The CodeWithThiru Platform separates its multi-module architecture into distinct logical layers. This layered approach guarantees that components are decoupled, reusable, and strictly scoped.

## 1. App Layer
**Location:** `:app`

The App layer is the single executable module of the application. Its primary responsibilities include:
*   **Application Class:** Initialization of Dagger Hilt, WorkManager, Firebase, and global error handlers.
*   **Dependency Wiring:** Providing concrete implementations for top-level application interfaces.
*   **Navigation Host:** Setting up the global Compose Navigation graph that stitches together the individual feature graphs.
*   **Global UI Shell:** Managing the root `Scaffold`, bottom navigation bar, and global snackbars.

## 2. Feature Layers
**Location:** `:feature:*` (e.g., `:feature:auth`, `:feature:payments`)

Feature modules are vertically sliced, self-contained units of user-facing functionality.
*   **Presentation:** Contains Jetpack Compose UI screens, ViewModels, and UI state models.
*   **Business Logic:** Contains UseCases and business rules specific to the feature.
*   **Independence:** A feature module must NEVER depend on another feature module. If features must communicate, they do so through the core data layer or by passing IDs via navigation routes.

## 3. Core Layer
**Location:** `:core:*` (e.g., `:core:network`, `:core:database`, `:core:billing`)

The Core layer contains all infrastructure, data access, and third-party integrations. These modules are strictly feature-agnostic.
*   `core:network`: Configures Retrofit, OkHttp, and network interceptors.
*   `core:database`: Contains Room Database configurations, DAOs, and entities.
*   `core:datastore`: Manages local key-value and proto storage using Jetpack DataStore.
*   `core:billing`: Wraps the Google Play Billing Library to provide a simplified API for SaaS subscription management.
*   `core:monetization`: Manages Google AdMob configurations and ad unit loading.
*   `core:analytics`: Wraps Firebase Analytics and Crashlytics for consistent event logging.
*   `core:common`: Provides fundamental Kotlin extensions, base classes, and Dispatcher injections.

## 4. Design System Layer
**Location:** `:core:designsystem`

Although technically a core module, the Design System represents the visual foundation of the platform.
*   **Theme:** Defines custom Compose `Colors`, `Typography`, and `Shapes`.
*   **Components:** Contains reusable, highly customized UI widgets (e.g., `ThiruButton`, `ThiruTextField`, `ThiruTopAppBar`).
*   **Assets:** Houses shared icons, fonts, and illustrations.
*   **Goal:** By forcing all feature modules to use `core:designsystem`, the application guarantees visual consistency and enables instantaneous, app-wide UI updates.
