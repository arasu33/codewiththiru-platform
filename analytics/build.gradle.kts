/**
 * ⚠️ CONSUMER SETUP REQUIRED — Firebase Analytics
 *
 * This module uses Firebase Analytics (via Firebase BOM). Consumers MUST:
 *
 * 1. Place `google-services.json` in the app/ module root
 *    → Download from Firebase Console > Project Settings > Your App
 *
 * 2. Apply the Google Services plugin in your app/build.gradle.kts:
 *    plugins { id("com.google.gms.google-services") }
 *
 * 3. Add to your app's AndroidManifest.xml:
 *    <uses-permission android:name="android.permission.INTERNET" />
 *    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *
 * 4. (Optional) Android 13+ advertising ID:
 *    <uses-permission android:name="com.google.android.gms.permission.AD_ID" />
 *
 * Without google-services.json, FirebaseAnalytics will throw IllegalStateException at runtime.
 *
 * See: docs/modules/analytics.md for full integration guide.
 */
plugins {
    id("codewiththiru.android-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.serialization)
    jacoco
}

group = "com.codewiththiru.platform"

android {
    namespace = "com.codewiththiru.platform.analytics"
}

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
}

// JaCoCo test coverage setup for Android
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter =
        listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*",
        )
    val debugTree =
        fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
            exclude(fileFilter)
        }
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(
        fileTree(layout.buildDirectory.get()) {
            include("jacoco/testDebugUnitTest.exec")
        },
    )
}

dependencies {
    implementation(project(":analytics-api"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore.preferences)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation("org.robolectric:robolectric:4.12")
    testImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation(libs.androidx.test.ext.junit)
}
