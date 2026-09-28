# 📝 Feedback Module

Offline-first feedback and bug reporting collection module built with Jetpack Compose.

## Features
- **Offline Persistence**: Draft auto-saving with AES-256 GCM encryption.
- **PII Redaction**: Automatic masking of emails and phone numbers in logs.
- **Attachments**: Support for adding screenshots or logs.
- **Validation**: Form validation and required field checks.
- **Decoupled Submission**: Bring your own backend (or just send an email intent).

## Quick Start
1. Add dependency:
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:feedback")
}
```

2. Render UI:
```kotlin
val config = FeedbackConfig.Builder()
    .addCategory("bug", "Bug Report")
    .addCategory("feature", "Feature Request")
    .build()

// Provide your own submission logic
val submissionProvider = object : FeedbackSubmissionProvider {
    override suspend fun submit(payload: FeedbackPayload): Result<Unit> {
        // e.g., POST to your API
        return Result.success(Unit)
    }
}

FeedbackScreen(
    config = config,
    // ...
)
```

## Consumer Prerequisites
- **Zero-config core**: Does not require Firebase or Play Services.
- **Storage**: If using the attachment picker, the host app may need to handle `READ_MEDIA_IMAGES` or storage permissions depending on Android version.
