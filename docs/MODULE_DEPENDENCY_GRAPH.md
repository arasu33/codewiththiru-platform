# Module Dependency Graph

The following Mermaid diagram visualizes the dependency graph of the CodeWithThiru Platform. 

Arrows indicate dependencies (e.g., `app` depends on `feature:auth`). To avoid circular dependencies and ensure high build concurrency, feature modules do not depend on one another. Instead, they interact via interfaces, implicit navigation, or core shared state.

```mermaid
graph TD
    %% App Layer
    App[app]

    %% Feature Layer
    Auth[feature:auth]
    Dashboard[feature:dashboard]
    Profile[feature:profile]
    Subscription[feature:subscription]

    %% Core Layer
    DesignSystem[core:designsystem]
    Network[core:network]
    Database[core:database]
    Datastore[core:datastore]
    Analytics[core:analytics]
    Billing[core:billing]
    Monetization[core:monetization]
    Common[core:common]

    %% Dependencies
    App --> Auth
    App --> Dashboard
    App --> Profile
    App --> Subscription

    Auth --> DesignSystem
    Auth --> Network
    Auth --> Datastore
    Auth --> Common

    Dashboard --> DesignSystem
    Dashboard --> Network
    Dashboard --> Database
    Dashboard --> Analytics
    Dashboard --> Common

    Profile --> DesignSystem
    Profile --> Network
    Profile --> Datastore
    Profile --> Common

    Subscription --> DesignSystem
    Subscription --> Network
    Subscription --> Billing
    Subscription --> Common

    %% Core internal dependencies
    DesignSystem --> Common
    Network --> Common
    Database --> Common
    Datastore --> Common
    Analytics --> Common
    Billing --> Common
    Monetization --> Common
```

> [!TIP]
> This graph is kept as flat as possible. The `core:common` module sits at the very bottom and contains generic utilities, extensions, and base classes used across the entire platform.
