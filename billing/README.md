# Enterprise Billing & Subscriptions Platform

Module: `:billing`
Version: 1.0.0

## Features
- Provider Agnostic Architecture (Google Play Billing Default)
- Subscription & Entitlement Engine
- Dynamic Paywalls
- Offer, Coupon, and Gamification Integrations
- Revenue Analytics & Fraud Security

## Architecture Overview
The platform decouples business logic from specific billing providers. The `BillingManager` interfaces with standard applications, orchestrating through a pure domain `PurchaseStateMachine` and relying on the `GooglePlayBillingProvider` for the underlying execution.

## Documentation
- [API_GUIDE.md](docs/API_GUIDE.md)
- [SECURITY.md](docs/SECURITY.md)
- [SUBSCRIPTIONS.md](docs/SUBSCRIPTIONS.md)
- [ENTITLEMENTS.md](docs/ENTITLEMENTS.md)
- [PAYWALLS.md](docs/PAYWALLS.md)

## Status: RELEASED
