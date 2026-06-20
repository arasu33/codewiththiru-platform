# Changelog

All notable changes to the `:about` module will be documented in this file.

## [1.0.0] - 2026-06-19
### Added
- Initial release of the decoupled `about` module.
- `AboutConfig.Builder` and immutable configuration states.
- Support for `AppInfo`, `DeveloperInfo`, `DeviceInfo`, and `LegalInfo`.
- UI abstractions via `AboutUiState` (`Loading`, `Error`, `Success`).
- Centralized `AboutEventListener` interface.
- Diagnostics copy logic with payload preview dialog via `CustDialog`.
- Baseline Roborazzi snapshot verification.
