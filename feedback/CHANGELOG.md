# Changelog

## [0.3.0] - 2026-06-20
### Added
- Phase 8.6 Hardening Sprint implementation.
- `FeedbackViewModel` MVI state management decoupled from UI.
- `FeedbackPrivacyConfig` and `DraftStorageConfig`.
- `EncryptedDraftStorage` via AndroidX Security Crypto (`1.0.0`).
- Roborazzi visual regression tests.
- TalkBack semantic enhancements to `CategorySelector` and `AttachmentsSection`.

### Changed
- Replaced Enum-based categories with extensible `FeedbackCategory` data class.
- Migrated legacy `DataStoreDraftProvider` plaintext usage to `FeedbackDraftProviderImpl` with encrypted fallback logic.

### Security
- Resolved critical vulnerability storing PII in SharedPreferences plaintext. All drafts are now encrypted via AES256-GCM.
