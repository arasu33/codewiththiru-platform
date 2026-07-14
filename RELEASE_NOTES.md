# CodeWithThiru Platform Release Notes

This document provides a detailed overview of changes, deprecations, and bug fixes for external consumers of the CodeWithThiru Platform SDK.

## [1.0.4] - 2026-07-10 (Gold Master)

### Fixed
- Fixed critical coroutine cancellation issues in `GameTimer` preventing memory leaks.
- Replaced thread-unsafe data structures with `ConcurrentHashMap` across analytics and ads modules.

### Security
- Hardened Android Keystore configuration to explicitly require AES/GCM 256-bit encryption.
- Added strict ProGuard/R8 `consumer-rules.pro` to obfuscate internal SDK implementations while preserving the public API boundary.

### Governance & Architecture
- Enforced strict Clean Architecture boundaries (e.g., relocating `CouponRepository` to the domain layer).
- Converted hundreds of internal support classes and utility functions to `internal` visibility to drastically narrow the public SDK surface area.
- Adopted strict Binary Compatibility validation.

---

*Note: For historical development milestones leading up to 1.0.4, refer to the commit history.*
