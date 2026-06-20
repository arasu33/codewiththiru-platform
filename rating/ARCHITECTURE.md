# Rating Module Architecture

## Overview
The `:rating` module provides a decoupled, offline-safe, and behavioral-psychology-driven Rating Engine. It bridges positive user sentiment to Google Play In-App Reviews while diverting negative sentiment to the Feedback Module.

## Core Abstractions

### `RatingRepository`
The central source of truth for rating state, abstracting all interactions with storage and analytics.

### `RatingTriggerEngine`
Evaluates complex mathematical conditions to determine if the user is eligible to be prompted for a rating. It evaluates against:
- `RatingTriggerRules` (minimum launches, days installed, significant events)
- `RatingCooldownPolicy` (days since last prompt, dismissal, feedback redirect, or play review)

### `RatingStorageProvider`
Responsible for persistence (via `androidx.datastore`), securely tracking launch counts, event counts, and various prompt timestamps.

### `RatingAnalyticsProvider`
A DI-agnostic interface for emitting telemetry regarding rating funnels. Emits `RatingAnalyticsEvent` (e.g., `PromptShown`, `StarSelected`, `ReviewLaunched`).

### `PlayReviewProvider`
Abstracts the `com.google.android.play.core.review.ReviewManager`. Swallows all exceptions and maps them to `ReviewLaunchResult`.

## MVI State Management
`RatingViewModel` manages `RatingUiState` and consumes `RatingAction`. It emits side-effects via `RatingResult` which the UI layer responds to (e.g., launching Play Review or redirecting to Feedback).

## Separation of Concerns
The module strictly adheres to Clean Architecture:
- **UI Layer**: `RatingScreen`, `RatingDialog`, `RatingStars`
- **Presentation Layer**: `RatingViewModel`
- **Domain Layer**: `RatingTriggerEngine`, `RatingTriggerRules`, `RatingCooldownPolicy`
- **Data Layer**: `RatingRepository`, `RatingStorageProviderImpl`
- **Integration Layer**: `PlayReviewProviderImpl`, `RatingAnalyticsProvider`
