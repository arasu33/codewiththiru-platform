# Widgets Module (`:widgets`)

## Purpose
The `:widgets` module houses reusable, higher-level UI components built on top of the `:designsystem`. This includes custom AppBars, BottomSheets, standardized Dialogs, Loading overlays, and Empty State views.

## Architecture
- **Layer:** Presentation
- **Pattern:** Stateless Composable functions.
- **Components:** Components are grouped by UI pattern (e.g., `/dialogs`, `/appbars`, `/states`).

## Public APIs
- `ThiruTopAppBar`: Standardized navigation bar with back handling.
- `EmptyStateView`: Configurable illustration, title, and action button.
- `LoadingOverlay`: Full-screen transparent blocking loader.
- `StandardBottomSheet`: Wrapper around M3 `ModalBottomSheet` with platform-specific adjustments.

## Configuration
Components receive configuration via data classes or standard composable parameters.

## Dependencies
- `:designsystem`
- Coil (`io.coil-kt:coil-compose`) for image loading within widgets.

## Initialization
None required.

## Integration Steps
1. Add `implementation(project(":widgets"))`.
2. Import and use the composable functions.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- `EmptyStateView` can accept remote strings configured via Firebase Remote Config.

## Security
None.

## Accessibility
- All widgets are rigorously annotated with `semantics` modifiers.
- `contentDescription` is mandatory for all image-based widgets.

## Testing
- Compose UI Testing (`createComposeRule`) is used to verify interactions (e.g., clicking the back button in `ThiruTopAppBar`).

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Images not loading in Empty State | Coil uninitialized | Ensure ImageLoader is configured or fallback drawables are provided. |

## Examples
```kotlin
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            ThiruTopAppBar(
                title = "Settings",
                onNavigationIconClick = onBack
            )
        }
    ) { padding ->
        // Content
    }
}
```
