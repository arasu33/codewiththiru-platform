# Changelog

## [0.1.0] - 2026-06-20
### Added
- Initial creation of `:rating` module (Phase 9.2).
- Integrated `androidx.datastore` for rating tracking (`RatingStorageProvider`).
- Implemented `RatingTriggerEngine` for behavioral eligibility evaluation.
- Added explicit `RatingResult` and `RatingEligibilityResult` sealed interfaces for deterministic state outcomes.
- Centralized custom copy configurations via `RatingUiCustomization` pointing to localized `strings.xml`.
- Added Play Core `ReviewManager` abstraction via `PlayReviewProvider`.
- Custom, highly accessible Compose `RatingStars` component replacing standard sliders.
