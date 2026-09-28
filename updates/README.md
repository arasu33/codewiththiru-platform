# 🔄 Updates Module (`:updates`)

Flexible and immediate in-app update engine adhering to Google Play In-App Updates best practices, featuring customizable Compose UI for force updates and "What's New" release notes.

---

## ⚠️ Consumer Prerequisites & Setup

### 1. Google Play Console Setup
- App updates only work when the app is installed via Google Play.
- Sideloaded APKs or local debug builds will report that no update is available.
- To test flexible or immediate updates, upload builds with increasing version codes to an **Internal Test Track** or **Internal App Sharing** in Google Play Console.

### 2. Permissions
Add to your app's `AndroidManifest.xml`:
```xml
<!-- Required to check for and download update packages -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 3. Gradle Dependency (`build.gradle.kts`)
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
    implementation("com.codewiththiru.platform:updates")
}
```

---

## 🚀 Features

- **Flexible Updates**: Download updates silently in the background while users interact with the app, showing a restart snackbar upon completion.
- **Immediate (Force) Updates**: Full-screen blocking flow requiring users to update before proceeding when critical security or schema upgrades are detected.
- **WhatsNewDialog**: Compose Material 3 dialog presenting version changelogs and release notes on first launch after an update.
- **Policy Engine**: Configurable grace periods, minimum required version checking, and cellular/Wi-Fi constraints.

---

## 💡 Quick Start

### 1. Show Force Update Screen (Jetpack Compose)
```kotlin
// Fully customizable title, description, and button action
ForceUpdateScreen(
    title = "Update Required",
    message = "A critical update is required to continue using the app.",
    buttonText = "Update Now",
    onUpdateClicked = {
        updateManager.startImmediateUpdate(activity)
    }
)
```

### 2. Show What's New Dialog
```kotlin
if (showWhatsNew) {
    WhatsNewDialog(
        releaseNotes = """
            • Added Dark Mode support
            • Performance optimizations
            • Bug fixes and stability improvements
        """.trimIndent(),
        onDismiss = { showWhatsNew = false }
    )
}
```

---

## 🛠️ Common Pitfalls & Tips
- ❌ **Testing on debug builds**: Google Play's App Update API will return `UpdateAvailability.UPDATE_NOT_AVAILABLE`. Use Play Internal App Sharing instead.
- ❌ **Ignoring flexible update completion**: When a flexible update finishes downloading, prompt the user with a Snackbar to restart and install the update.
