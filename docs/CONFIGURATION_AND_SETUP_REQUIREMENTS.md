# Module Configuration & Setup Requirements

This document provides a comprehensive, module-by-module breakdown of all external prerequisites, credentials, configuration files, and permissions required to successfully integrate the CodeWithThiru Platform libraries into your host Android application.

---

## 📋 Summary of Module Prerequisites

The following table summarizes the key setup requirements for each platform module. Detailed configuration steps for each are provided in the sections below.

| Module | Required Credentials / Files | Required Permissions | Key Manifest Elements | Third-Party Console Setup |
| :--- | :--- | :--- | :--- | :--- |
| **`:analytics`** | `google-services.json` | None | None | Firebase Console Project |
| **`:ads`** | AdMob App ID, Ad Unit IDs | `AD_ID`, `INTERNET`, `ACCESS_NETWORK_STATE` | `com.google.android.gms.ads.APPLICATION_ID` | Google AdMob Console & UMP Consent |
| **`:notifications`**| `google-services.json` | `POST_NOTIFICATIONS` (API 33+) | `com.google.firebase.MESSAGING_EVENT` | Firebase Cloud Messaging (FCM) |
| **`:billing`** | In-App / Sub Product IDs | `com.android.vending.BILLING` | None | Google Play Console (Subscriptions/Products) |
| **`:remote-config`**| `google-services.json`, `remote_config_defaults.xml` | None | None | Firebase Remote Config Parameters |
| **`:security-platform`**| Keystore Alias / Encryption Key | `USE_BIOMETRIC` | None | Keystore Alias Registration |
| **`:ai-platform`** | Gemini API Key / Vertex AI Setup | None | None | Google Cloud Console / Vertex AI project |

---

## 🛠️ Step-by-Step Module Configuration

### 1. Analytics Module (`:analytics`)

The analytics module wraps Firebase Analytics to provide uniform event tracking.

#### Required Files & Plugins
*   **`google-services.json`**: Must be placed in the host application module's root directory:
    ```
    host-app/
    ├── src/
    ├── build.gradle.kts
    └── google-services.json  <-- Place here
    ```
*   **Google Services Plugin**: Apply the Google Services plugin in your host application's `build.gradle.kts` file:
    ```kotlin
    plugins {
        id("com.android.application")
        id("com.google.gms.google-services") // Required to process google-services.json
    }
    ```

> [!IMPORTANT]
> If the `google-services.json` file is missing or the Google Services Gradle plugin is not applied, Firebase Analytics will not initialize. The CodeWithThiru platform is hardened to catch this failure and fallback to a silent `NoOpAnalyticsProvider` instead of crashing.

---

### 2. Monetization & Ads Module (`:ads`)

Provides Banner, Interstitial, Rewarded, and App Open ad format integrations using the Google Mobile Ads SDK.

#### Required Manifest Metadata
You must specify your Google AdMob Application ID in the host app's `AndroidManifest.xml` inside the `<application>` tag:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application>
        <!-- Google AdMob Application ID (Replace with your actual App ID) -->
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713"/> <!-- Test App ID -->
    </application>
</manifest>
```

> [!WARNING]
> In stock AdMob integrations, failing to provide a valid `APPLICATION_ID` metadata tag in `AndroidManifest.xml` results in an immediate crash at application startup. The CodeWithThiru platform has been hardened to query this metadata value dynamically on startup. If the tag is missing, initialization is skipped and a detailed error log is generated, preventing application crashes.

#### Required Permissions
Declare the following permissions in the host app's `AndroidManifest.xml`:

```xml
<!-- General Network Permissions -->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

<!-- Required for personalized ads on Android 12 (API 31) and higher -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

#### Ad Unit ID Configuration
Register the Ad Unit IDs for the various ad formats. During development, **always** use test ad unit IDs.

| Ad Format | Production Source | Default Test Ad Unit ID |
| :--- | :--- | :--- |
| **Banner** | AdMob Console | `ca-app-pub-3940256099942544/6300978111` |
| **Interstitial** | AdMob Console | `ca-app-pub-3940256099942544/1033173712` |
| **Rewarded** | AdMob Console | `ca-app-pub-3940256099942544/5224354917` |
| **App Open** | AdMob Console | `ca-app-pub-3940256099942544/9257395921` |

---

### 3. Push Notifications Module (`:notifications`)

Integrates Firebase Cloud Messaging (FCM) for campaign push deliveries and localized user messages.

#### Required Files & Plugins
*   **`google-services.json`**: Must be placed in the host application module's root directory.
*   **Google Services Plugin**: Apply the `com.google.gms.google-services` plugin.

#### Required Permissions
Declare the notification runtime permission for Android 13 (API 33) and higher in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

> [!IMPORTANT]
> The host app must explicitly request the `POST_NOTIFICATIONS` runtime permission on screen startup. If denied, background pushes can still be processed, but system heads-up notifications will not show.

#### Service Declarations
Ensure your main application's manifest inherits or declares the platform FCM messaging service:

```xml
<service
    android:name="com.codewiththiru.notifications.provider.fcm.PlatformFcmMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

---

### 4. In-App Purchases & Billing Module (`:billing`)

Integrates Google Play Billing Library to manage subscription models and in-app product sales.

#### Required Permissions
Include the billing permission in `AndroidManifest.xml`:

```xml
<!-- Required for communicating with Google Play Billing Service -->
<uses-permission android:name="com.android.vending.BILLING"/>
```

#### Google Play Console Prerequisites
1.  Set up a **Merchant Account** in the Google Play Console.
2.  Add and publish your products under **Products > In-app products** or **Products > Subscriptions**.
3.  Configure matching **Product IDs** (SKUs) in your platform initialization properties:
    ```kotlin
    val billingManager = BillingManager.Builder(context)
        .addInAppProductId("premium_upgrade")
        .addSubscriptionProductId("monthly_pro_subscription")
        .build()
    ```

---

### 5. Remote Configuration Module (`:remote-config`)

Enables remote dynamic switches and key-value parameter updates via Firebase Remote Config.

#### Required Files
*   **`google-services.json`** placed in the host app directory.
*   **`remote_config_defaults.xml`**: A local XML file defining standard defaults when remote config cannot be fetched or is offline. Place this file in your host app's `res/xml/` resource folder:
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <!-- res/xml/remote_config_defaults.xml -->
    <defaultsMap>
        <entry>
            <key>ads_enabled</key>
            <value>true</value>
        </entry>
        <entry>
            <key>min_version_required</key>
            <value>1.0.0</value>
        </entry>
        <entry>
            <key>ai_chat_enabled</key>
            <value>false</value>
        </entry>
    </defaultsMap>
    ```

---

### 6. Security Module (`:security-platform`)

Manages secure token storage, database encryption, and biometric credential validation.

#### Required Permissions
For secure access control via fingerprint or face credentials, declare:

```xml
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
```

#### Keystore Setup
Ensure that the host application provides a unique Keystore alias for credential wrapping:
```kotlin
val securityProvider = SecurityProvider.Builder(context)
    .setKeyAlias("cwt_host_app_encryption_key")
    .setKeyValidityDuration(365) // Days
    .build()
```

---

### 7. AI Platform Module (`:ai-platform`)

Integrates AI platform services (such as Google Gemini Pro or local ML models).

#### API Key Configuration
Configure your private API key securely. **Never** hardcode this key in your version-controlled source files. Instead, pass it via Gradle configuration or fetch it dynamically:

1.  Add the key to your host project's local environment or `local.properties`:
    ```properties
    # local.properties
    GEMINI_API_KEY="AIzaSyYourGeminiApiKeyHere"
    ```
2.  Reference the value in the host app's `build.gradle.kts`:
    ```kotlin
    android {
        buildTypes {
            release {
                buildConfigField("String", "GEMINI_API_KEY", "\"${project.findProperty("GEMINI_API_KEY")}\"")
            }
            debug {
                buildConfigField("String", "GEMINI_API_KEY", "\"${project.findProperty("GEMINI_API_KEY")}\"")
            }
        }
    }
    ```

---

## 🛡️ Enterprise Hardening & Graceful Degradation

The CodeWithThiru Platform features a robust, enterprise-grade architecture engineered to guarantee maximum runtime stability. The platform is designed around **Graceful Degradation** principles, preventing crashes in scenarios where credentials or plugins are misconfigured:

1.  **Robust Firebase Instantiation**: Rather than referencing `Firebase` or `FirebaseAnalytics` directly (which causes immediate crashes when configuration is incomplete), providers lazily initialize using protected try-catch wrappers. If Firebase is absent, tracking automatically redirects to a local logcat provider, and Remote Config falls back to the local `remote_config_defaults.xml`.
2.  **Manifest Verification for AdMob**: The monetization module parses the host app's manifest metadata before invoking the AdMob SDK constructor. If the `APPLICATION_ID` is missing, it cancels initialization and logs a warning, rather than letting the SDK throw an uncaught startup exception.
3.  **FCM Safety Checks**: The notifications module validates standard Google Play Services availability before attempting register token requests, resolving issues on devices without Play Services (e.g. Huawei, custom ROMs) seamlessly.
