# Play Store Compliance Guide

Deploying the CodeWithThiru Platform applications to the Google Play Store requires strict adherence to Play Store policies. This document serves as a checklist and guide for maintaining compliance.

## 1. Data Safety Declaration

Google Play requires developers to accurately declare how their app collects, uses, and shares user data.

### Platform Data Collection Defaults
When filling out the Data Safety form, you must declare the following (if standard platform features are used):
*   **Authentication (Firebase):** Email address, User IDs (Collected, Not Shared, Required).
*   **Crashlytics:** Crash logs, Device or other IDs (Collected, Not Shared, Optional/Required based on consent).
*   **Analytics:** App interactions, Device IDs (Collected, Not Shared, Optional).
*   **AdMob:** If using the Ads module, you must declare Device or other IDs are collected and shared with third parties for Advertising purposes.

### Data Deletion Policy
You must provide a clear mechanism for users to request account and data deletion.
*   **In-App:** Ensure the `Settings` screen includes a "Delete Account" option.
*   **Web Form:** Provide a URL in the Play Console pointing to a web-based data deletion request form.

## 2. Ads Declaration & Families Policy

### Ads
If your app variant includes the `feature-ads` module (AdMob):
1.  Navigate to **App Content > Ads** in the Play Console.
2.  Select "Yes, my app contains ads."
3.  Ensure your app displays a CMP (Consent Management Platform) message for GDPR/CCPA compliance. The platform uses Google's UMP SDK for this.

### Families Policy
If your app is not specifically designed for children:
1.  Navigate to **Target Audience and Content**.
2.  Select appropriate age groups (e.g., 18 and over).
3.  Declare that the app does *not* appeal to children.

## 3. Target API Level Requirements

Google Play enforces minimum Target API levels for new apps and updates.
*   **Current Requirement (as of mid-2024):** Apps must target API level 34 (Android 14) or higher.
*   **Action:** Regularly update `targetSdk` in the `libs.versions.toml` catalog.

```toml
[versions]
targetSdk = "34"
compileSdk = "34"
minSdk = "24"
```

When updating `targetSdk`, ensure you review behavioral changes introduced in the new Android version (e.g., foreground service types, granular media permissions).

## 4. Google Play Billing

Apps selling digital goods or services must use the Google Play Billing Library.
*   The platform integrates Play Billing via the `core-billing` module.
*   **Compliance:** Do not use third-party payment gateways (Stripe, PayPal) for digital content like SaaS subscriptions.
*   **Subscriptions:** Ensure your app clearly states subscription terms, duration, and cancellation policies on the paywall screen, as per Play Store Subscription policies.

## 5. App Permissions

Only request permissions that are absolutely necessary for the core functionality of the app.
*   **Location:** If requesting location in the background, you must submit a declaration and video demonstrating why it is necessary.
*   **Notifications (`POST_NOTIFICATIONS`):** Request this dynamically in context (Android 13+), rather than at app launch.
