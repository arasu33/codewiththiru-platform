/**
 * ⚠️ CONSUMER SETUP REQUIRED — Google Play Billing
 *
 * This module uses Google Play Billing Library (billing-ktx). Consumers MUST:
 *
 * 1. Add to your app's AndroidManifest.xml:
 *    <uses-permission android:name="com.android.vending.BILLING" />
 *
 * 2. Create in-app products or subscriptions in Google Play Console:
 *    → Play Console > Your App > Monetize > Products
 *
 * 3. Upload a signed APK/AAB to Play Console (at least internal test track):
 *    → Billing ONLY works with signed builds distributed via Play Store
 *    → Will NOT work on emulators without Play Store or sideloaded debug builds
 *
 * 4. Set up license testing accounts in Play Console:
 *    → Play Console > Settings > License testing > Add test accounts
 *
 * Without Play Store and proper setup, BillingClient will return
 * BillingResponseCode.BILLING_UNAVAILABLE.
 *
 * See: docs/modules/billing.md for full integration guide.
 */
plugins {
    alias(libs.plugins.android.library)
    id("codewiththiru.publishing")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.codewiththiru.billing"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    

    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    implementation(project(":billing-api"))
    implementation(project(":core"))
    implementation(project(":analytics-api"))
    implementation(project(":remote-config-api"))
    implementation(project(":notifications-api"))

    
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    
    // UI
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Lifecycle
    implementation(libs.androidx.lifecycle.process)
    
    // Play Billing (will be added via toml later)
    implementation("com.android.billingclient:billing-ktx:9.1.0") // Updated to latest to support new monetization features
    
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)
}
