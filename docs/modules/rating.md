# Rating Module (`:rating`)

## Purpose
The `:rating` module manages in-app review prompts using the Google Play Core API. It abstracts the complex logic of deciding *when* to prompt the user to ensure it's not intrusive.

## Architecture
- **Layer:** Domain/Utilities
- **Pattern:** Manager / Repository pattern.
- **Components:**
  - `AppRatingManager`: Encapsulates Play Core `ReviewManager`.
  - `RatingRulesEngine`: Evaluates conditions (sessions, positive actions) to determine eligibility.

## Public APIs
- `AppRatingManager.checkAndShowRating(activity: Activity)`: Evaluates rules and shows prompt if eligible.
- `AppRatingManager.logPositiveAction()`: Increments the internal counter of happy paths (e.g., successful task completion).

## Configuration
Requires thresholds to be configured (e.g., min 5 sessions, min 3 positive actions).

## Dependencies
- `:core`
- Play Review (`com.google.android.play:review`, `com.google.android.play:review-ktx`)

## Initialization
Inject `AppRatingManager` via Hilt. No explicit startup initialization required.

## Integration Steps
1. Add `implementation(project(":features:rating"))`.
2. Call `logPositiveAction()` when a user completes a core flow.
3. Call `checkAndShowRating()` at a natural pause in the app (e.g., returning to the home screen).

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- Evaluates `rating_min_sessions` and `rating_prompt_delay_days` to remotely adjust aggressively prompting users.
- Tracks `in_app_review_shown` and `in_app_review_completed`.

## Security
None.

## Accessibility
- UI is entirely managed by the Android OS (Google Play Services), ensuring compliance.

## Testing
- Use `FakeReviewManager` provided by Play Core for instrumented tests.
- Unit test `RatingRulesEngine` by mocking `DataStore` counters.

## Migration
- Ensure migration from older Play Core monolithic library to the specialized `com.google.android.play:review` artifact.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Prompt never shows | Quota exhausted / Rules | Play Store limits prompts. Use Internal App Sharing to force show it for testing. |

## Examples
```kotlin
// In MainActivity.kt or a ViewModel connected to UI events
fun onUserCompletedTask() {
    ratingManager.logPositiveAction()
    
    lifecycleScope.launch {
        ratingManager.checkAndShowRating(this@MainActivity)
    }
}
```
