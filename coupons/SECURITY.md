# Security & Audit Guidelines

The `:coupons` module handles sensitive promotional and financial unlock data.

## Threat Model & Mitigations
1. **Brute Force Attacks**: Prevented via `CouponCooldownEngine`. Implement the injected `CouponCooldownPolicy` to enforce strict IP/Account thresholds.
2. **Local Tampering**: Local validation models must be stored using `EncryptedCouponStorageProvider`. Plaintext local caching is prohibited in production.
3. **Payload Spoofing**: `CouponIntegrityProvider` is available to hook into Play Integrity API to ensure the binary requesting the coupon redemption is untampered.
4. **Replay Attacks**: `RedemptionHistoryTracker` guarantees `maxRedemptionsPerUser` and `allowMultipleRedemptions` constraints locally, while `CouponRepository.redeemCoupon()` should execute idempotency checks on the backend.
