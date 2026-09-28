# 🚀 CodeWithThiru Platform SDK

[![CI](https://github.com/arasu33/codewiththiru-platform/actions/workflows/develop-workflow.yml/badge.svg)](https://github.com/arasu33/codewiththiru-platform/actions/workflows/develop-workflow.yml)
[![Version](https://img.shields.io/badge/version-1.5.1-blue.svg)](gradle.properties)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Modules](https://img.shields.io/badge/modules-51%20active-success.svg)](MODULE_STATUS.md)

An enterprise-grade, modular Android library platform powering scalable mobile applications and gaming titles. Built with 100% Kotlin, Jetpack Compose, Coroutines/Flow, and clean modular architecture.

---

## 📦 What's Inside?

The platform consists of **51 active, decoupled modules** organized into four foundational tiers:

| Layer | Modules | Highlights |
|---|---|---|
| **Foundation & UI** | `:core`, `:core-android`, `:designsystem`, `:widgets` | Material 3 tokens, unbranded customizable components (`PlatformTopAppBar`, `EmptyStateView`, `StandardBottomSheet`), AndroidX Startup integration. |
| **Feature Suite** | `:about`, `:feedback`, `:rating`, `:onboarding`, `:settings`, `:consent`, `:more-apps`, `:updates`, `:coupons` | Ready-to-use Compose screens and flows with zero forced branding—100% customizable out of the box. |
| **Enterprise Infrastructure** | `:analytics`, `:ads`, `:billing`, `:notifications`, `:identity`, `:security`, `:security-platform`, `:remote-config`, `:sync-backup`, `:observability-platform`, `:growth-platform`, `:ai-platform`, `:platform-framework`, `:platform-governance` | Biometrics, encrypted storage, Keystore encryption, multi-provider auth, dynamic configuration, crash reporting, and deep linking. |
| **Gaming & Mechanics** | `:game-events`, `:game-sync`, `:game-profile`, `:game-achievements`, `:game-rewards`, `:game-statistics`, `:game-leaderboard`, `:developer:*` | Event-driven architecture, cross-device game saves, leaderboard engine, and automated testing mocks. |

For detailed documentation on every module, see [MODULE_STATUS.md](MODULE_STATUS.md) and [docs/ALL_MODULES_INTEGRATION.md](docs/ALL_MODULES_INTEGRATION.md).

---

## ⚡ Quick Start

### 1. Configure Repository (`settings.gradle.kts`)
Authenticate with GitHub Packages or Maven Local:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/arasu33/codewiththiru-platform")
            credentials {
                username = System.getenv("GPR_USER") ?: "your_github_username"
                password = System.getenv("GPR_KEY") ?: "your_personal_access_token"
            }
        }
    }
}
```

### 2. Add BOM & Modules (`build.gradle.kts`)
Use the Bill of Materials (BOM) to align all platform dependency versions:

```kotlin
dependencies {
    // Platform BOM (single version source of truth)
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))

    // Foundation & UI
    implementation("com.codewiththiru.platform:designsystem")
    implementation("com.codewiththiru.platform:widgets")

    // Features
    implementation("com.codewiththiru.platform:settings")
    implementation("com.codewiththiru.platform:onboarding")
    implementation("com.codewiththiru.platform:consent")

    // Security & Infrastructure
    implementation("com.codewiththiru.platform:security")
    implementation("com.codewiththiru.platform:analytics")
}
```

---

## ⚙️ Consumer Configuration & Prerequisites Checklist

Depending on which modules your application consumes, verify the following prerequisites:

| Module | Prerequisite | Instructions / Required Snippet |
|---|---|---|
| `:ads` | **AdMob App ID** | Declare `<meta-data android:name="com.google.android.gms.ads.APPLICATION_ID" android:value="ca-app-pub-..." />` in `AndroidManifest.xml` to prevent startup crash. |
| `:analytics`, `:notifications` | **Firebase Configuration** | Place `google-services.json` in your app module and apply `id("com.google.gms.google-services")`. |
| `:notifications` | **Runtime Permission** | Request `android.permission.POST_NOTIFICATIONS` at runtime on Android 13+ (API 33+). |
| `:more-apps` | **Android 11+ Package Visibility** | Add `<queries><intent><action android:name="android.intent.action.VIEW" /><data android:scheme="market" /></intent></queries>` to manifest. |
| `:billing` | **Google Play Console** | Configure in-app products/subscriptions in Play Console and test with license testers on a signed internal test track build. |
| `:core-android` | **Context Initialization** | Auto-initialized via AndroidX App Startup `PlatformInitializer`. Zero boilerplate required. |

---

## 🛡️ Security & Privacy

Security is built into every layer of the platform:
- **Zero Secrets**: No hardcoded API keys, keystores, or certificates are stored in this repository.
- **Biometrics & Keystore**: Hardware-backed AES-256-GCM encryption via Android Keystore (`:security`, `:security-platform`).
- **Private Vulnerability Disclosure**: Please review our [SECURITY.md](SECURITY.md) for instructions on securely reporting vulnerabilities.

---

## 🤝 Contributing

We welcome community contributions, bug reports, and feature proposals!
- Review [CONTRIBUTING.md](CONTRIBUTING.md) for local setup, development workflows, and PR requirements.
- Review [docs/BRANCHING_STRATEGY.md](docs/BRANCHING_STRATEGY.md) for branch topology, lifecycle, and tagging policies.
- Abide by our [Code of Conduct](CODE_OF_CONDUCT.md).
- Create issues using our [Issue Templates](.github/ISSUE_TEMPLATE/).

---

## 📄 License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
