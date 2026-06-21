# Coupons Module (`:coupons`)

## Purpose
The `:coupons` module provides the UI and logic for promo code redemption. This allows users to unlock premium features by entering a code, integrating directly with Google Play Billing.

## Architecture
- **Layer:** Feature / Billing Domain
- **Pattern:** MVVM
- **Components:**
  - `CouponRedemptionDialog`: UI for entering the code.
  - `CouponManager`: Interfaces with the `:billing` (or `:core`) module to validate the code.

## Public APIs
- `CouponRedemptionDialog()`
- `CouponManager.redeemCode(code: String)`

## Configuration
Requires Play Console promo codes to be set up and associated with in-app products or subscriptions.

## Dependencies
- `:core`, `:designsystem`, `:widgets`
- Google Play Billing (`com.android.billingclient:billing-ktx`)

## Initialization
Billing Client must be connected prior to validating a code.

## Integration Steps
1. Add `implementation(project(":features:coupons"))`.
2. Provide an entry point (e.g., a "Redeem Code" button in the Premium Upgrade screen).

## Required Permissions
```xml
<uses-permission android:name="com.android.vending.BILLING" />
```

## Manifest Entries
None specifically for coupons, but Billing permission is required.

## Remote Config & Analytics Dependencies
- `enable_promo_codes`: Allows turning off the feature remotely if abuse is detected.
- Tracks `promo_code_attempted`, `promo_code_success`, `promo_code_invalid`.

## Security
- Client-side validation is insufficient. The app must fetch the updated purchase token from Play Billing and optionally verify it server-side.

## Accessibility
- Text fields have appropriate `keyboardOptions` and error state readouts.

## Testing
- Test using Play Store License Testing accounts with generated test codes.
- Unit test `CouponManager` by mocking the `BillingClient` responses.

## Migration
- Ensure compatibility with Billing Library v6/v7 standards.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Code invalid | Skus mismatch / Inactive | Verify the code is active in the Play Console and associated with a base plan. |
| Nothing happens on success | Cache issue | Query purchases explicitly after redemption to update UI state. |

## Examples
```kotlin
@Composable
fun UpgradeScreen() {
    var showCouponDialog by remember { mutableStateOf(false) }

    Button(onClick = { showCouponDialog = true }) {
        Text("Redeem Promo Code")
    }

    if (showCouponDialog) {
        CouponRedemptionDialog(
            onDismiss = { showCouponDialog = false },
            onSuccess = { /* Unlock premium UI */ }
        )
    }
}
```
