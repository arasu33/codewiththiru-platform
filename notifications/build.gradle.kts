/**
 * ⚠️ CONSUMER SETUP REQUIRED — Firebase Cloud Messaging (FCM)
 *
 * This module uses Firebase Messaging (via Firebase BOM). Consumers MUST:
 *
 * 1. Place `google-services.json` in the app/ module root
 *    → Download from Firebase Console > Project Settings > Your App
 *    → Ensure Cloud Messaging is enabled in the Firebase Console
 *
 * 2. Apply the Google Services plugin in your app/build.gradle.kts:
 *    plugins { id("com.google.gms.google-services") }
 *
 * 3. Add to your app's AndroidManifest.xml:
 *    <uses-permission android:name="android.permission.INTERNET" />
 *
 * 4. Android 13+ (API 33): Request POST_NOTIFICATIONS runtime permission:
 *    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
 *    → Must be requested at runtime via ActivityCompat.requestPermissions()
 *
 * 5. (Recommended) Configure default notification channel & icon in manifest:
 *    <meta-data android:name="com.google.firebase.messaging.default_notification_channel_id"
 *               android:value="@string/default_notification_channel_id" />
 *    <meta-data android:name="com.google.firebase.messaging.default_notification_icon"
 *               android:resource="@drawable/ic_notification" />
 *
 * Without google-services.json, Firebase Messaging will fail silently.
 *
 * See: docs/modules/notifications.md for full integration guide.
 */
plugins {
    alias(libs.plugins.android.library)
    id("codewiththiru.publishing")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.codewiththiru.notifications"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":notifications-api"))
    implementation(project(":core"))
    implementation(project(":analytics-api"))
    implementation(project(":remote-config-api"))


    implementation(project(":designsystem"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)
    
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
