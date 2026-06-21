# Sample App Guide

The CodeWithThiru Platform comes with a built-in sample app (located in the `:app` module) that serves as a reference implementation of the platform's architecture, modules, and best practices.

## Overview

The showcase app demonstrates a complete SaaS application lifecycle, integrating:
*   **Onboarding & Auth:** Firebase Email/Password and Google Sign-In.
*   **Premium Features:** A paywall integrating Google Play Billing (`core-billing`).
*   **Monetization:** Banner and Interstitial ads via AdMob (`feature-ads`).
*   **Background Processing:** WorkManager integration for offline data syncing (`core-work`).
*   **UI/UX:** A comprehensive Jetpack Compose design system.

## How to Run the App

1.  **Clone the repository:** Ensure you have the latest code.
2.  **Firebase Configuration:** 
    *   Create a project in the Firebase Console.
    *   Download `google-services.json` and place it in the `app/` directory.
3.  **Local Properties:** Ensure `local.properties` has standard Android SDK paths. If testing billing or ads, you may need to define mock API keys here.
4.  **Build and Run:** Select the `app` run configuration in Android Studio and deploy to an emulator or device running API 24+.

## Architecture Showcase

The `app` module is intentionally thin. It is responsible solely for:
1.  **Dependency Injection Setup:** Bootstrapping Hilt components.
2.  **Navigation Graph:** Tying together destinations from various `feature-*` modules.
3.  **Application Class:** Initializing crash reporting, analytics, and background worker factories.

## Certification Report (Internal QA)

Before any major platform release, the Sample App must pass the following manual certification matrix.

| Feature Area | Test Scenario | Status | Notes |
| :--- | :--- | :--- | :--- |
| **Authentication** | Sign up new user, sign out, sign in existing user. | Pending | Verify session persistence. |
| **Billing** | Purchase monthly subscription (test card), verify premium content unlocks. | Pending | Check Play Store purchase cache. |
| **Ads** | Trigger Interstitial ad upon completing a core action. | Pending | Verify UMP consent dialog appears first. |
| **WorkManager** | Trigger forced offline sync, disable network, verify retry logic. | Pending | Inspect via App Inspection tool. |
| **Accessibility** | Navigate entire core flow using TalkBack. | Pending | Ensure > 48dp touch targets on custom views. |
| **Performance** | Profile startup time using Macrobenchmark. | Pending | Target: < 1.5s cold start. |
