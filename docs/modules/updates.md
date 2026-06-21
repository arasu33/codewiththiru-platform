# Updates Module (`:updates`)

## Purpose
The `:updates` module seamlessly handles in-app updates using the Google Play Core App Update API. It supports both Flexible (background download) and Immediate (forced blocking) update flows.

## Architecture
- **Layer:** Domain/Utilities
- **Pattern:** Wrapper/Manager.
- **Components:**
  - `UpdateManager`: Abstracts the interaction with `AppUpdateManager`.

## Public APIs
- `UpdateManager.checkForUpdate(activity, updateType)`
- `UpdateManager.registerListener()`: For tracking flexible download progress.

## Configuration
Requires mapping of Update Priorities (assigned in Google Play Console during release) to update types (Flexible vs Immediate).

## Dependencies
- `:core`
- App Update (`com.google.android.play:app-update`, `com.google.android.play:app-update-ktx`)

## Initialization
Usually instantiated in `MainActivity` since it requires Activity context to launch the update flow.

## Integration Steps
1. Add `implementation(project(":features:updates"))`.
2. In `MainActivity.onCreate`, instantiate and call `checkForUpdate()`.
3. Override `onActivityResult` (or use `ActivityResultContracts`) to handle update failures or cancellations.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- `force_update_version_code`: A fallback remote config to force an immediate update if the Play Store priority system fails or takes too long to propagate.
- Tracks `update_prompt_shown`, `update_accepted`, `update_failed`.

## Security
- Relies on Google Play Services signature verification for the APKs.

## Accessibility
- Handled by Google Play Services dialogs.

## Testing
- Utilize `FakeAppUpdateManager` to simulate updates in Espresso tests without actually contacting the Play Store.

## Migration
- Moved away from the monolithic Play Core library to the standalone `app-update` dependency.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Update not triggering | Play Store Cache | Clear cache of the Google Play Store app on the testing device. |
| Immediate update stuck | Lifecycle issue | Ensure `checkForUpdate` is also called in `onResume` to resume an immediate update if the app was backgrounded. |

## Examples
```kotlin
class MainActivity : ComponentActivity() {
    @Inject lateinit var updateManager: UpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check for updates
        lifecycleScope.launch {
            updateManager.checkForUpdate(
                activity = this@MainActivity,
                allowedType = AppUpdateType.FLEXIBLE
            )
        }
    }
    
    override fun onResume() {
        super.onResume()
        updateManager.resumeImmediateUpdateIfInProgress(this)
    }
}
```
