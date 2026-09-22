# 💳 Billing Module

Google Play Billing Library wrapper for in-app purchases and subscriptions with offline resilience and purchase verification.

## ⚠️ Consumer Prerequisites

Before using this module, your consumer app **must** complete the following setup:

### 1. Google Play Console Setup
1. Go to [Google Play Console](https://play.google.com/console/)
2. Navigate to **Monetize > Products > In-app products** or **Subscriptions**
3. Create your product IDs (e.g., `premium_monthly`, `remove_ads`)
4. **Upload a signed APK/AAB** to at least the internal test track
   > ⚠️ Billing will NOT work until you've uploaded at least one signed build

### 2. Permissions
Add to your app's `AndroidManifest.xml`:
```xml
<uses-permission android:name="com.android.vending.BILLING" />
```

### 3. License Testing
1. Go to Play Console > **Settings > License testing**
2. Add your test Gmail accounts
3. These accounts can make test purchases without being charged

### 4. BOM Dependency
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:<version>"))
    implementation("com.codewiththiru.platform:billing")
}
```

## Features
- **Purchase management**: One-time purchases and subscriptions
- **Purchase verification**: Server-side and client-side verification
- **Offline resilience**: Pending purchases are tracked and completed when connectivity returns
- **Price display**: Localized price formatting from Play Store
- **Lifecycle-aware**: Automatic BillingClient connection management
- **Compose UI**: Paywall and subscription management screens

## Architecture
```
billing-api (interfaces) ← billing (implementation)
                                ↓
                        GooglePlayBillingProvider
                        BillingConnectionManager
                        PurchaseVerifier
                        SubscriptionManager
```

## Important Limitations
| Scenario | Result |
|---|---|
| Debug build (not signed) | `BILLING_UNAVAILABLE` |
| Sideloaded APK | `BILLING_UNAVAILABLE` |
| Emulator without Play Store | `BILLING_UNAVAILABLE` |
| No products in Play Console | Empty product list |
| No signed build uploaded | `DEVELOPER_ERROR` |

## Common Pitfalls
- ❌ Missing BILLING permission → `BillingResponseCode.BILLING_UNAVAILABLE`
- ❌ Debug/unsigned build → Billing client cannot connect
- ❌ No products configured in Play Console → Empty queries
- ❌ Not consuming one-time purchases → Purchase stuck in "pending"
- ✅ Always handle `BillingResponseCode.SERVICE_DISCONNECTED` with reconnect
- ✅ Test with license testing accounts for sandbox purchases
