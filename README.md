# CodeWithThiru Platform 🚀

[![CI/CD Build](https://github.com/arasu33/codewiththiru-platform/actions/workflows/build.yml/badge.svg)](https://github.com/arasu33/codewiththiru-platform/actions/workflows/build.yml)
[![Documentation](https://img.shields.io/badge/docs-GitHub%20Pages-blue.svg)](https://arasu33.github.io/codewiththiru-platform/)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)]()
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.x-purple.svg)](https://kotlinlang.org/)

An enterprise-grade, modern Android multi-module Software-as-a-Service (SaaS) foundation framework built to accelerate modular Android development.

> 📖 **Read the Full Documentation**: The complete API guides, module reference, architecture blueprints, and compliance standards are available at **[https://arasu33.github.io/codewiththiru-platform/](https://arasu33.github.io/codewiththiru-platform/)**.

---

## 🏛️ Platform Architecture

The platform follows clean architecture principles, strict unidirectional data flow (UDF), and separation of concerns across a multi-layer modular setup:

```mermaid
graph TD
    subgraph App Layer
        sample-app[Sample Showcase App]
    end

    subgraph Feature Modules
        ads[Ads Module]
        billing[Billing Module]
        identity[Identity/Auth Module]
        ai[AI Native Module]
        notifications[Notifications Module]
        updates[App Updates Module]
        coupons[Coupons Module]
    end

    subgraph Core Framework Layer
        core[Core Base Library]
        designsystem[Design System]
        security[Security & Encryption]
        observability[Observability & Logging]
        remote-config[Remote Config]
    end

    sample-app --> Feature Modules
    Feature Modules --> Core Framework Layer
```

---

## 🛠️ Modules Reference

The platform contains over 20 modular libraries split logically:

| Category | Module Directory | Description |
| :--- | :--- | :--- |
| **Foundation** | [`:core`](core/) | Base classes, DI setup (Hilt), network utilities, thread dispatchers. |
| | [`:security-platform`](security-platform/) | Data encryption, keystore management, obfuscation, root detection. |
| | [`:observability-platform`](observability-platform/) | Structured logging, crash reporting (Crashlytics), performance monitoring. |
| **UI & UX** | [`:design-system`](design-system/) | Theme tokens, typography, colors, dark/light palette configurations. |
| | [`:widgets`](widgets/) | Reusable jetpack compose components, custom layouts. |
| | [`:about`](about/) | Standardized and customizable "About" screen. |
| | [`:feedback`](feedback/) | User feedback and reporting tools. |
| **Growth** | [`:analytics`](analytics/) | Unified tracking analytics interface (Firebase, custom endpoints). |
| | [`:remote-config`](remote-config/) | Real-time feature flagging and experimentations. |
| | [`:notifications`](notifications/) | Local & Push Notification handlers using FCM and WorkManager. |
| | [`:updates`](updates/) | In-app updates check and forced updates system. |
| **Monetization** | [`:ads`](ads/) | Google Mobile Ads (AdMob) integration with UMP consent flows. |
| | [`:billing`](billing/) | Google Play Billing API integration for subscriptions & products. |
| **Intelligence** | [`:ai-platform`](ai-platform/) | AI core capabilities leveraging Gemini and on-device ML Kit models. |

---

## 🚀 Getting Started

### Prerequisites
* **Android Studio:** Jellyfish | 2023.3.1 or newer.
* **JDK:** Version 17
* **Android SDK:** API 24 (minSdkVersion) or higher.

### Quick Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/arasu33/codewiththiru-platform.git
   cd codewiththiru-platform
   ```

2. **Sync and build**
   Run the Gradle build to fetch all dependencies:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Run Unit Tests**
   ```bash
   ./gradlew testDebugUnitTest
   ```

4. **Launch Showcase App**
   Open the codebase in Android Studio and run the `:sample-app` module on a connected device/emulator.

---

## 📚 Documentation Site Setup (Local MkDocs)

To preview the documentation locally:

1. Install Python dependencies:
   ```bash
   pip install mkdocs-material pymdown-extensions
   ```

2. Run the local documentation server:
   ```bash
   mkdocs serve
   ```

3. Open your browser and navigate to `http://localhost:8000`.

---

## 🛡️ License & Copyright
This repository is private property. All rights reserved. Please contact the platform administrators for distribution rights.
