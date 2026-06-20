# Architecture Guide

The `:coupons` module is built using **Clean Architecture** patterns combined with **Unidirectional Data Flow (UDF)**.

## Layer Overview
- **Domain Layer**: Contains pure Kotlin models (`CouponModel`, `CouponReward`, `CouponResult`) and abstract engines (`CouponValidator`, `RedemptionPolicyEvaluator`).
- **Data Layer**: Houses `CouponRepository` and local abstractions (`CouponStorageProvider`).
- **Presentation Layer**: Exposes `CouponUIState`, accepts `CouponIntent`, and emits `CouponEffect`.

## The Validation Engine
Operates using the composite pattern (`CompositeCouponModelValidator`). Rules (`ExpiryValidationRule`, `CampaignValidationRule`) independently return a `CouponErrorCode?`.

## The Redemption Engine
Separates the verification of limits (`RedemptionPolicyEvaluator`) from the physical reward injection (`RewardProcessor`). It guarantees atomicity when integrated correctly with backend endpoints.
