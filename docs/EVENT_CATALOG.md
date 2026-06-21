# Analytics Event Catalog

This document details the standard taxonomy for analytics events emitted by the CodeWithThiru Platform. These events are logged to Firebase Analytics and synced to our internal data lake for business intelligence reporting.

## Standard Event Taxonomy

All events follow a strictly typed structure to ensure data consistency across multiple host apps.
*   **Format:** `noun_verb` (e.g., `ad_click`, `screen_view`)
*   **Casing:** `snake_case`

## 1. Lifecycle & Engagement Events

Track user retention, session lengths, and navigation.

| Event Name | Parameters | Description | Trigger |
| :--- | :--- | :--- | :--- |
| `app_open` | `is_cold_start` (Boolean) <br> `network_type` (String) | Records when the user brings the app to the foreground. | Application `onResume` |
| `screen_view` | `screen_name` (String) <br> `screen_class` (String) | User views a distinct screen/composable. | Compose `DisposableEffect` / Fragment `onResume` |
| `onboarding_complete` | `time_taken_sec` (Int) | User finishes the initial onboarding tutorial. | Clicking "Finish" on the final onboarding slide. |
| `push_notification_open` | `campaign_id` (String) <br> `topic` (String) | User taps a remote push notification. | PendingIntent resolution. |

## 2. Monetization (Ads) Events

Detailed tracking of the advertising funnel.

| Event Name | Parameters | Description | Trigger |
| :--- | :--- | :--- | :--- |
| `ad_request` | `ad_format` (String) <br> `ad_unit_id` (String) | An attempt is made to load an ad. | SDK `loadAd()` called. |
| `ad_impression` | `ad_format` (String) <br> `network` (String) <br> `revenue` (Double) | An ad is successfully shown to the user. | Ad network impression callback. |
| `ad_click` | `ad_format` (String) | User clicks on the displayed ad. | Ad network click callback. |
| `ad_fail_to_load` | `ad_format` (String) <br> `error_code` (Int) | An ad request returns no fill or errors out. | Ad network error callback. |

## 3. Billing & Subscription Events

Tracking the funnel for in-app purchases.

| Event Name | Parameters | Description | Trigger |
| :--- | :--- | :--- | :--- |
| `paywall_view` | `entry_point` (String) | The subscription offer screen is displayed. | Paywall compose function renders. |
| `checkout_start` | `sku_id` (String) <br> `price` (Double) | User initiates the Google Play billing flow. | User taps "Subscribe". |
| `purchase_success` | `sku_id` (String) <br> `order_id` (String) | A transaction is successfully completed. | Google Play Billing `onPurchasesUpdated` (Success). |
| `purchase_cancel` | `sku_id` (String) | User closes the billing bottom sheet without buying. | Google Play Billing `onPurchasesUpdated` (User Canceled). |

## 4. System & Error Events

Operational health metrics.

| Event Name | Parameters | Description | Trigger |
| :--- | :--- | :--- | :--- |
| `api_error` | `endpoint` (String) <br> `status_code` (Int) | A network call to the backend fails. | Retrofit/OkHttp error interceptor. |
| `db_migration_fail` | `from_version` (Int) <br> `to_version` (Int) | Room database migration crashes. | SQLiteOpenHelper `onUpgrade` exception. |
