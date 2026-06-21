# Billing Module

## Purpose
The `billing` module encapsulates the Google Play Billing Library. It manages in-app purchases (IAPs), subscriptions, premium feature unlocks, and handles transaction state synchronization with the CodeWithThiru backend.

## Architecture
The module uses an MVI-style architecture:
- **BillingClientWrapper**: Low-level interface with Play Billing SDK.
- **BillingRepository**: Maintains local state of user purchases and handles server verification via Real-Time Developer Notifications (RTDN).
- **BillingViewModel**: Exposes `StateFlow` of available products and active subscriptions to the UI.

## Public APIs
- `BillingManager`: Initializes the connection to the Google Play Store.
- `launchBillingFlow(activity: Activity, productDetails: ProductDetails)`: Initiates a purchase.
- `observePremiumState()`: Returns a `Flow<Boolean>` representing the user's premium status.

## Configuration
Product IDs and Base Plan IDs must be defined in the Google Play Console and mirrored locally in a configuration class or fetched via Remote Config.

## Dependencies
```gradle
implementation("com.android.billingclient:billing-ktx:6.2.1")
```

## Initialization
Connect to the BillingClient as early as possible (e.g., in `Application.onCreate` or main Activity) to query active purchases and restore state.
```kotlin
val billingClient = BillingClient.newBuilder(context)
    .setListener(purchasesUpdatedListener)
    .enablePendingPurchases()
    .build()
billingClient.startConnection(billingStateListener)
```

## Integration Steps
1. Add the BILLING permission.
2. Configure products in the Play Console.
3. Set up backend server to receive RTDN from Google Cloud Pub/Sub.
4. Implement the premium Paywall UI in the app.

## Required Permissions
- `com.android.vending.BILLING`

## Manifest Entries
The Play Billing library automatically injects necessary manifest entries. No manual entries are needed.

## Remote Config & Analytics Dependencies
- **Remote Config**: Used to dynamically alter subscription pricing tiers or offer discounts without an app update.
- **Analytics**: Logs `ecommerce_purchase`, `subscription_started`, `subscription_canceled`.

## Security
**Never** trust client-side validation alone. All purchases (`purchaseToken`) must be sent to your secure backend to be validated with the Google Play Developer API before granting premium access.

## Accessibility
Ensure the paywall clearly reads out the subscription terms, price, and auto-renewal policies to comply with Play Store accessibility and policy guidelines.

## Testing
1. Add tester Google accounts in the Play Console.
2. Use Test Cards provided by Google Play in the purchase flow.
3. Test pending transactions (e.g., slow networks, cash payments).

## Migration
When upgrading from Billing V4/V5 to V6, ensure you migrate from SKUs to the new `ProductDetails` and `SubscriptionOfferDetails` paradigms.

## Troubleshooting
- **ITEM_ALREADY_OWNED**: The user has an unconsumed purchase. Ensure one-time purchases are consumed via `consumeAsync()`.
- **Products not showing**: Ensure the app is published to an internal testing track and the tester account is opted-in.

## Examples
```kotlin
fun buySubscription(activity: Activity, productDetails: ProductDetails) {
    val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken
    val billingFlowParams = BillingFlowParams.newBuilder()
        .setProductDetailsParamsList(
            listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .setOfferToken(offerToken ?: "")
                    .build()
            )
        ).build()
    billingClient.launchBillingFlow(activity, billingFlowParams)
}
```
