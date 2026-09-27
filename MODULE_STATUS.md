# Module Status & Inventory

Current Platform Release: **v1.4.1**

| Layer | Module | Status | Type | Description |
|---|---|---|---|---|
| **Foundation** | `:core` | Stable | Kotlin JVM | Common logger, result, dispatchers, date/string utils |
| **Foundation** | `:core-android` | Stable | Android AAR | App Startup auto-init, device info, platform diagnostics |
| **Foundation** | `:designsystem` | Stable | Android AAR | Jetpack Compose M3 theme, tokens, base components |
| **Presentation** | `:widgets` | Active | Android AAR | High-level Compose widgets (TopAppBar, BottomSheet, EmptyState) |
| **Presentation** | `:about` | Stable | Android AAR | About Screen, legal links, app diagnostics display |
| **Presentation** | `:feedback` | Stable | Android AAR | Offline-first feedback collector with encrypted drafts |
| **Presentation** | `:rating` | Stable | Android AAR | Behavioral In-App Review with smart prompt throttling |
| **Presentation** | `:more-apps` | Stable | Android AAR | Cross-promotion catalog with install detection |
| **Presentation** | `:onboarding` | Active | Android AAR | Walkthrough carousel, feature tour, first-launch tracking |
| **Presentation** | `:settings` | Active | Android AAR | Theme selector, notification toggles, preferences screen |
| **Analytics** | `:analytics-api` | Stable | Kotlin JVM | Domain contracts & models for analytics |
| **Analytics** | `:analytics` | Stable | Android AAR | Offline-first Firebase Analytics engine with PII scrubbing |
| **Consent** | `:consent` | Active | Android AAR | GDPR/CCPA privacy consent manager & UI banner/dialogs |
| **Monetization** | `:billing-api` | Stable | Kotlin JVM | Domain contracts for in-app purchases |
| **Monetization** | `:billing` | Stable | Android AAR | Google Play Billing Library v9 wrapper with offline state |
| **Monetization** | `:coupons` | Stable | Android AAR | Coupon code redemption, validation & anti-fraud |
| **Monetization** | `:ads-api` | Stable | Kotlin JVM | Ads contracts and state models |
| **Monetization** | `:ads` | Stable | Android AAR | AdMob and Google UMP integration |
| **Configuration** | `:remote-config-api` | Stable | Kotlin JVM | Remote config contracts |
| **Configuration** | `:remote-config` | Stable | Android AAR | Firebase Remote Config with encrypted local cache |
| **Communications**| `:notifications-api` | Stable | Kotlin JVM | Push notification contracts |
| **Communications**| `:notifications` | Stable | Android AAR | Firebase Cloud Messaging engine with rich notifications |
| **Distribution** | `:updates` | Stable | Android AAR | Google Play In-App Updates & "What's New" dialog |
| **Identity** | `:identity` | Stable | Android AAR | Multi-provider authentication, sessions, device trust |
| **Security** | `:security` | Active | Android AAR | Consumer security toolkit: SecureStorage, Biometrics, Root check |
| **Security** | `:security-platform`| Stable | Android AAR | Platform security, threat detection, cert pinning, vault |
| **Sync** | `:sync-backup` | Stable | Android AAR | Cloud backup & background sync engine |
| **Gamification** | `:gamification` | Stable | Android AAR | Streaks, achievements, level progression |
| **Growth** | `:growth-platform` | Stable | Android AAR | Ecosystem growth aggregator |
| **Observability** | `:observability-platform`| Stable | Android AAR | Performance monitoring, breadcrumbs, health monitors |
| **Governance** | `:platform-governance` | Stable | Android AAR | Architecture rule enforcement & compliance |
| **Framework** | `:platform-framework` | Stable | Android AAR | Shared framework utilities |
| **AI** | `:ai-platform` | Stable | Android AAR | AI inference & model management |
| **AI** | `:ai-native-platform` | Stable | Android AAR | AI-native app orchestration |
| **BOM** | `:platform-bom` | Stable | Java Platform | Maven Bill of Materials aligning all platform versions |
| **Games** | `:game-common` | Stable | Kotlin JVM | Common game engine utilities |
| **Games** | `:game-save` | Stable | Kotlin JVM | Game save serialization |
| **Games** | `:game-statistics` | Stable | Kotlin JVM | Player statistics tracker |
| **Games** | `:game-achievements`| Stable | Kotlin JVM | Game achievements engine |
| **Games** | `:game-rewards` | Stable | Kotlin JVM | Game reward distribution |
| **Games** | `:game-audio` | Stable | Kotlin JVM | Audio effects engine |
| **Games** | `:game-haptics` | Stable | Kotlin JVM | Vibration & haptic feedback |
| **Games** | `:game-challenges` | Stable | Kotlin JVM | Daily/weekly challenges |
| **Games** | `:game-profile` | Stable | Kotlin JVM | Player profile management |
| **Games** | `:game-settings` | Stable | Kotlin JVM | Game settings storage |
| **Games** | `:game-sync` | Stable | Kotlin JVM | Cloud save synchronization |
| **Games** | `:game-leaderboard` | Stable | Kotlin JVM | Leaderboard tracking |
| **Games** | `:game-events` | Stable | Kotlin JVM | In-game telemetry events |
| **Developer** | `:developer:test-utils` | Stable | Kotlin JVM | Test assertions, fakes, and coroutine test rules |
| **Developer** | `:developer:game-testing` | Stable | Kotlin JVM | Game engine test harnesses |
| **Developer** | `:developer:benchmark` | Stable | Kotlin JVM | Microbenchmarks |
| **Developer** | `:developer:inspection`| Stable | Kotlin JVM | Code inspection tools |
| **Developer** | `:developer-platform`| Stable | Android AAR | Developer tooling facade |
