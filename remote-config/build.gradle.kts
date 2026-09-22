/**
 * ⚠️ CONSUMER SETUP REQUIRED — Firebase Remote Config
 *
 * This module uses Firebase Remote Config (via Firebase BOM). Consumers MUST:
 *
 * 1. Place `google-services.json` in the app/ module root
 *    → Download from Firebase Console > Project Settings > Your App
 *
 * 2. Apply the Google Services plugin in your app/build.gradle.kts:
 *    plugins { id("com.google.gms.google-services") }
 *
 * 3. Add to your app's AndroidManifest.xml:
 *    <uses-permission android:name="android.permission.INTERNET" />
 *
 * 4. (Recommended) Set up Remote Config defaults:
 *    → In Firebase Console: Remote Config > Add parameters
 *    → Or locally via XML: res/xml/remote_config_defaults.xml
 *
 * Without google-services.json, Remote Config will throw at initialization.
 * The module includes local caching via DataStore for offline resilience.
 *
 * See: docs/modules/remote-config.md for full integration guide.
 */
plugins {
    alias(libs.plugins.android.library)
    id("codewiththiru.publishing")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.codewiththiru.remoteconfig"
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
}

dependencies {
    implementation(project(":remote-config-api"))
    implementation(project(":core"))
    implementation(project(":analytics-api"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.config)
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)
    
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
