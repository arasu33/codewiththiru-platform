# Coupons Module

The `:coupons` module is an enterprise-grade SDK for Android that handles coupon entry, local/remote validation, fraud detection, and redemption flows. It is built strictly using Clean Architecture, Unidirectional Data Flow, and robust security and accessibility standards.

## Features
- **Validation Engine**: Configurable offline and remote validation rules (Expiry, Campaign limits, Format).
- **Redemption Engine**: Sophisticated policy evaluator limiting reuse and tracking history.
- **Security First**: Cooldowns, Anti-fraud hooks, Integrity verification, and Encrypted storage scaffolding.
- **Accessibility**: First-class support for `LiveRegionMode.Polite` and semantic UI properties in Compose.
- **Extensible Analytics**: Sealed event classes for unified telemetry.

## Usage
Include the module in your `build.gradle.kts`:
```kotlin
implementation(project(":coupons"))
```
For API specifics, refer to [API_GUIDE.md](API_GUIDE.md).
