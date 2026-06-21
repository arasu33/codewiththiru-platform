# Troubleshooting Guide

This guide covers common issues encountered while developing on the CodeWithThiru Platform and how to resolve them.

## Build and Compilation Errors

### 1. JVM Target Incompatibility
**Error:** `Cannot inline bytecode built with JVM target 17 into bytecode that is being built with JVM target 1.8.`
**Fix:** Ensure your Java toolchain and Kotlin compile options are set to Java 17 across all modules.
Check your `build.gradle.kts` files or the shared build logic plugin:
```kotlin
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlinOptions {
    jvmTarget = "17"
}
```

### 2. Gradle Cache Corruption
**Error:** Unexplained Unresolved References or `TransformException`.
**Fix:** 
1. Stop Gradle daemons: `./gradlew --stop`
2. Clean the project: `./gradlew clean`
3. Invalidate Caches in Android Studio: `File > Invalidate Caches... > Invalidate and Restart`
4. Delete the `.gradle` folder in your project root.

## Firebase Integration Issues

### Missing `google-services.json`
**Error:** `File google-services.json is missing. The Google Services Plugin cannot function without it.`
**Fix:** 
1. Go to the Firebase Console.
2. Navigate to Project Settings.
3. Download the `google-services.json` file for your Android app package.
4. Place the file inside the `app/` module directory.

## AdMob / Monetization Issues

### AdMob Missing App ID
**Error:** `The Google Mobile Ads SDK was initialized incorrectly. AdMob App ID must be in the AndroidManifest.xml`
**Fix:** Ensure your `AndroidManifest.xml` inside the `app` module contains the metadata tag inside the `<application>` block.
```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"/>
```
*(For testing, use the Google test app ID: `ca-app-pub-3940256099942544~3347511713`)*

## Jetpack Compose Previews

### Previews Not Rendering
**Error:** "Render problem" or infinite loading in the Compose Preview pane.
**Fix:**
1. Ensure your Composable function does not inject ViewModels directly. Pass state and lambda callbacks instead.
2. Check for UI-thread blocking operations in the Composable.
3. Force a build: `Build > Rebuild Project`.

### Hilt and Previews
**Error:** `java.lang.IllegalStateException: Hilt view models cannot be created in a preview.`
**Fix:** Decouple your UI from the ViewModel for previews.
```kotlin
// Good for Previews
@Composable
fun ProfileScreen(state: ProfileState, onAction: (ProfileAction) -> Unit) { ... }

// Use this wrapper for Navigation/Activity level
@Composable
fun ProfileRoute(viewModel: ProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    ProfileScreen(state = state, onAction = viewModel::submitAction)
}
```
