# Comprehensive Platform Integration Guide

This guide details the step-by-step instructions to integrate the **CodeWithThiru Platform** modules into a new Android client application. It maps out dependencies, credentials (`google-services.json`, AdMob IDs, etc.), manifest permissions, initialization, and advanced customization options.

---

## 🏛️ General Setup

### 1. Repository Authentication
The platform packages are hosted on **GitHub Packages**. In your new project's `settings.gradle.kts`, configure the Maven repository:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/arasu33/codewiththiru-platform")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "YOUR_GITHUB_USERNAME"
                password = System.getenv("GITHUB_TOKEN") ?: "YOUR_GITHUB_PERSONAL_ACCESS_TOKEN" // Classic PAT with read:packages scope
            }
        }
    }
}
```

### 2. Root Build Configuration
Apply the Google Services plugin in your root-level `build.gradle.kts` if you are using Firebase-powered modules (Analytics, Remote Config, Notifications):

```kotlin
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("com.android.library") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}
```

In your application module's `build.gradle.kts` (`:app`):

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") // Required for Firebase integration
}
```

---

## 🛠️ Modules Reference & Setup Requirements

The platform uses a Bill of Materials (`platform-bom`) to synchronize versions. Declare it in your dependencies block:

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.0.0-SNAPSHOT"))
}
```

### 1. Foundation & Core Modules

#### A. `:core` & `:core-android`
The bedrock framework supplying Dependency Injection rules, standard Coroutine Dispatchers, and `CustResult` models.

*   **Requirements:**
    *   `android.permission.INTERNET`
    *   `android.permission.ACCESS_NETWORK_STATE`
*   **Dependencies:**
    ```kotlin
    implementation("com.codewiththiru.platform:core")
    implementation("com.codewiththiru.platform:core-android")
    ```
*   **Initialization (Application Class):**
    ```kotlin
    import android.app.Application
    import com.codewiththiru.platform.core.CodeWithThiru
    import com.codewiththiru.platform.core.config.PlatformConfig
    import com.codewiththiru.platform.core.config.Environment

    class HostApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            val config = PlatformConfig.Builder()
                .setEnvironment(if (BuildConfig.DEBUG) Environment.STAGING else Environment.PRODUCTION)
                .build()
            CodeWithThiru.initialize(this, config)
        }
    }
    ```

#### B. `:security-platform`
Handles data encryption (AES/GCM via Android Keystore), root & emulator detection, and runtime integrity checks.

*   **Dependencies:**
    ```kotlin
    implementation("com.codewiththiru.platform:security-platform")
    ```
*   **Usage / API Integration:**
    ```kotlin
    val securityManager = SecurityManager(context)
    if (securityManager.isDeviceRooted() || securityManager.isEmulator()) {
        // Enforce compliance / terminate application
    }
    
    // Encrypting user preferences
    val encryptedText = securityManager.encrypt("sensitive-api-token")
    val originalText = securityManager.decrypt(encryptedText)
    ```

#### C. `:observability-platform`
Centralized logging API supporting multiple concurrent plugins (such as Console logging, local file rotates, and Crashlytics).

*   **Dependencies:**
    ```kotlin
    implementation("com.codewiththiru.platform:observability-platform")
    ```
*   **Customization:** Pluggable printers can be registered during initialization.
    ```kotlin
    val logger = CustLogger.Builder()
        .addPrinter(ConsoleLogPrinter())
        .addPrinter(FirebaseCrashlyticsPrinter())
        .build()
    ```

---

### 2. Monetization & Billing

#### A. `:ads` & `:ads-api`
Wraps Google Mobile Ads (AdMob) SDK and User Messaging Platform (UMP) consent flows with dynamic frequency capping and debug verification.

*   **Required Configuration Files:** None, but requires configuration entries inside the Manifest.
*   **Manifest Settings:** Add your AdMob application ID to `AndroidManifest.xml` within the `<application>` element:
    ```xml
    <manifest ...>
        <!-- Required for personalized ads -->
        <uses-permission android:name="com.google.android.gms.permission.AD_ID" />
        
        <application ...>
            <meta-data
                android:name="com.google.android.gms.ads.APPLICATION_ID"
                android:value="ca-app-pub-3940256099942544~3347511713" /> <!-- Replace with actual AdMob App ID -->
        </application>
    </manifest>
    ```
*   **Custom Ad Resolution:**
    By default, `PlatformAdUnitResolver` prevents using production IDs in Debug builds. To test, use Google's official Test Ad Unit IDs.
*   **Remote Config Switches:**
    *   `ads_enabled`: Global kill-switch (`Boolean`).
    *   `ads_interstitial_enabled`: Control interstitial ad logic (`Boolean`).

#### B. `:billing` & `:billing-api`
A reactive wrapper around Google Play Billing SDK for parsing, validating, and restoring in-app purchases and subscriptions.

*   **Required Configuration:** Google Play Console License Key (for local receipt validation if enabled).
*   **Manifest Settings:**
    ```xml
    <uses-permission android:name="com.android.vending.BILLING" />
    ```
*   **API Usage:**
    ```kotlin
    billingClient.purchaseFlow.collect { purchaseState ->
        when (purchaseState) {
            is PurchaseState.Success -> grantEntitlement(purchaseState.productId)
            is PurchaseState.Error -> showError(purchaseState.errorMessage)
        }
    }
    ```

---

### 3. Growth & User Engagement

#### A. `:analytics` & `:analytics-api`
A facade routing usage telemetry into multiple target trackers like Firebase Analytics and your backend endpoints.

*   **Required Config Files:** Requires `google-services.json` at `app/google-services.json`.
*   **Dependencies:**
    ```kotlin
    implementation("com.codewiththiru.platform:analytics")
    ```
*   **API Usage:**
    ```kotlin
    analyticsTracker.logEvent(
        eventName = "purchase_completed",
        params = mapOf("price" to 9.99, "currency" to "USD")
    )
    ```

#### B. `:remote-config` & `:remote-config-api`
Manages feature toggles and remote overrides backed by Firebase Remote Config.

*   **Required Config Files:** Requires `google-services.json` at `app/google-services.json`.
*   **Setup Default Values:**
    Create a `res/xml/remote_config_defaults.xml` file in your app project:
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <defaultsMap>
        <entry>
            <key>ads_enabled</key>
            <value>true</value>
        </entry>
    </defaultsMap>
    ```

#### C. `:notifications` & `:notifications-api`
Manages FCM token synchronization and schedules rich local notifications.

*   **Required Config Files:** Requires `google-services.json` at `app/google-services.json`.
*   **Manifest Settings:**
    ```xml
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" /> <!-- Required for API 33+ -->
    
    <service
        android:name="com.codewiththiru.platform.notifications.CwtFirebaseMessagingService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>
    ```

#### D. `:updates`
Wraps the Google Play In-App Updates API, triggering flexible or immediate update prompts.

*   **Required Configuration:** None. Works automatically when connected to the Google Play Store environment.
*   **API Usage:**
    ```kotlin
    updateManager.checkForUpdates(activity, updateType = UpdateType.IMMEDIATE)
    ```

---

### 4. Intelligence & AI Platform

#### `:ai-platform`
Enables Generative AI workloads using the Google Gemini SDK (leveraging remote endpoints) and local ML Kit capabilities.

*   **Required Configuration:** Gemini API Key.
*   **Setup:** Pass your Gemini API Key in the `PlatformConfig` or store it securely in a native binary wrapper:
    ```kotlin
    val config = PlatformConfig.Builder()
        .setGeminiApiKey("AIzaSy...")
        .build()
    ```
*   **API Usage:**
    ```kotlin
    val response = aiManager.generateContent("Explain Android Jetpack Compose in one sentence.")
    ```

---

### 5. UI & Styling

#### `:designsystem`
The single source of truth for color definitions, typographic scaling, spacing indices, elevation depths, and interactive component previews.

*   **Dependencies:**
    ```kotlin
    implementation("com.codewiththiru.platform:designsystem")
    ```
*   **Compose Theme Integration:**
    ```kotlin
    import com.codewiththiru.platform.designsystem.theme.CustTheme

    @Composable
    fun HostAppScreen() {
        CustTheme {
            Surface(color = CustTheme.colors.background) {
                Text(
                    text = "Hello World!",
                    style = CustTheme.typography.bodyLarge,
                    color = CustTheme.colors.onBackground
                )
            }
        }
    }
    ```

---

## 📋 Pre-Launch Requirements Checklist

Before releasing your application to the Google Play Store, verify that the following configurations are finalized:

| Module | Requirement | Target Destination | Verification Method |
| :--- | :--- | :--- | :--- |
| **All Firebase Modules** | `google-services.json` | `app/` root directory | Run compilation; check for standard Firebase connection prints in Logcat. |
| **Ads Module** | AdMob App ID | `AndroidManifest.xml` | Confirm AdMob App ID is verified on the Google AdMob dashboard. |
| **Ads Module** | Ad Unit IDs | Remote Config / Strings | Confirm `PlatformAdUnitResolver` throws no security violation errors in STAGING environment. |
| **Billing Module** | Play Console License Key | Play Console Dashboard | Test purchases using License Testers configuration. |
| **AI Platform** | Gemini API Key | Secure Config | Ensure API key is obfuscated (not hardcoded directly into public repositories). |
