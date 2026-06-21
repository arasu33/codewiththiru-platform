# Feedback Module (`:feedback`)

## Purpose
The `:feedback` module allows users to submit bug reports, suggestions, or contact support directly from within the application. It supports device info collection and screenshot attachments.

## Architecture
- **Layer:** Feature
- **Pattern:** MVVM with Clean Architecture.
- **Components:**
  - `FeedbackScreen`: UI for the feedback form.
  - `FeedbackViewModel`: Validates input and triggers submission.
  - `FeedbackRepository`: Handles the API request to the support backend.

## Public APIs
- `FeedbackScreen()`: The main UI composable.
- `FeedbackManager`: Singleton utility to trigger silent logs or contextual feedback triggers.

## Configuration
Provide the support API endpoint and API key via Hilt bindings.

## Dependencies
- `:core`
- `:widgets`
- `androidx.activity:activity-compose` (for file pickers).

## Initialization
None.

## Integration Steps
1. Add `implementation(project(":features:feedback"))`.
2. Add `feedbackNavGraph()` to your app's navigation.

## Required Permissions
To attach existing media files:
```xml
<!-- For Android 12 and below -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<!-- For Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
```

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- `enable_feedback_attachments`: Remote config toggle to disable image uploads if server load is high.
- Tracks `feedback_submitted` events.

## Security
- **PII Scrubbing:** `FeedbackManager` strips out recognized email formats and phone numbers from logs before transmitting.

## Accessibility
- Form fields specify `imeAction = ImeAction.Next` for smooth keyboard traversal.

## Testing
- `FeedbackRepositoryTest`: Mocks the support backend API.
- `PIIScrubberTest`: Ensures regex correctly removes sensitive data.

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Upload fails immediately | Payload too large | Ensure bitmaps are compressed before sending (max 2MB). |

## Examples
```kotlin
Button(onClick = { navController.navigate("feedback_route") }) {
    Text("Report a Bug")
}
```
