# Migration Guide

## Upgrading to v1.0

This is the initial release of the `:coupons` module. No migration from previous internal monolithic coupon implementations is provided automatically.

### Steps to migrate manually:
1. Replace standard string validations with `CouponValidator`.
2. Move your discount models to `CouponReward`. Note that Double has been replaced with `BigDecimal` to prevent precision loss during financial operations.
3. Replace custom callbacks with `CouponAnalyticsEvent`.
