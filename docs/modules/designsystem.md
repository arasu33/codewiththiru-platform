# Design System Module (`:designsystem`)

## Purpose
The `:designsystem` module centralizes all UI styling, Jetpack Compose themes, typography, colors, and core shapes. It ensures visual consistency across the entire CodeWithThiru Platform.

## Architecture
- **Layer:** Presentation
- **Pattern:** Material Design 3 (M3) extension.
- **Components:**
  - `Theme.kt`: Main theme wrapper (`ThiruTheme`).
  - `Type.kt`: Typography scales and custom fonts.
  - `Color.kt`: Semantic color palettes for Light/Dark modes.
  - `Shape.kt`: Corner shape definitions.

## Public APIs
- `ThiruTheme`: The root composable theme wrapper.
- `ThiruTypography`: Access to typography tokens.
- `ThiruColors`: Access to custom semantic color tokens not covered by standard M3.

## Configuration
`ThemeConfig` data class can be injected to force dark/light overrides bypassing system defaults.

## Dependencies
- Compose Material 3 (`androidx.compose.material3:material3`)
- Compose UI (`androidx.compose.ui:ui`)
- Google Fonts (`androidx.compose.ui:ui-text-google-fonts`)

## Initialization
Wrap your top-level UI in the custom theme. No special initialization required at the application level.

## Integration Steps
1. Add `implementation(project(":designsystem"))`.
2. Wrap your screens in `ThiruTheme`.

## Required Permissions
None.

## Manifest Entries
None.

## Remote Config & Analytics Dependencies
- Evaluates `enable_holiday_theme` via Firebase Remote Config to toggle festive color palettes dynamically.

## Security
None.

## Accessibility
- Defines high-contrast color mappings for accessibility modes.
- Enforces minimum touch target sizes (48dp) through custom modifiers.

## Testing
- Uses Paparazzi and Roborazzi for automated snapshot testing of theming across different device form factors and dark/light modes.

## Migration
- When migrating from Material 2, ensure all `MaterialTheme.colors` calls are updated to `MaterialTheme.colorScheme`.

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Colors reverting to purple | Missing Theme | Ensure `ThiruTheme` wraps the problematic composable. |
| Custom fonts not loading | Network issue | The `ui-text-google-fonts` relies on Play Services. Ensure fallback fonts are configured. |

## Examples
```kotlin
@Composable
fun MyScreen() {
    ThiruTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Text(
                text = "Hello World",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
```
