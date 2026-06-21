# More Apps Module (`:more-apps`)

## Purpose
The `:more-apps` module provides a cross-promotion screen to showcase other applications developed by the CodeWithThiru team. It fetches app metadata from a remote JSON source.

## Architecture
- **Layer:** Feature
- **Pattern:** MVVM
- **Components:**
  - `MoreAppsScreen`: LazyColumn displaying apps.
  - `MoreAppsViewModel`: Fetches data and maps UI state.
  - `MoreAppsRepository`: Handles network calls to fetch the JSON payload.

## Public APIs
- `MoreAppsScreen()`
- `moreAppsNavGraph()`

## Configuration
Requires the endpoint URL defining the apps JSON payload.

## Dependencies
- `:core`, `:designsystem`, `:widgets`
- Coil for app icons.

## Initialization
None.

## Integration Steps
1. Add `implementation(project(":features:more-apps"))`.
2. Expose the entry point in your app's drawer or settings menu.

## Required Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Manifest Entries
To determine if an app is already installed and change the CTA to "Open" instead of "Install":
```xml
<queries>
    <package android:name="com.codewiththiru.app1" />
    <package android:name="com.codewiththiru.app2" />
</queries>
```

## Remote Config & Analytics Dependencies
- `more_apps_json_url`: Remotely configure the source URL.
- Tracks `cross_promo_clicked` with the destination package name.

## Security
- The repository strictly validates the incoming JSON against expected data classes to prevent malformed data injection.

## Accessibility
- Content descriptions on app icons (`"Icon for ${app.name}"`).
- Clear semantics for the "Install" / "Open" buttons.

## Testing
- Use MockWebServer to simulate the JSON payload.

## Migration
N/A

## Troubleshooting
| Issue | Cause | Solution |
|-------|-------|----------|
| Buttons say "Install" when app is installed | Missing `<queries>` | Android 11+ requires package visibility declarations. |

## Examples
```kotlin
// JSON Structure expected by Repository:
[
  {
    "id": "com.codewiththiru.notes",
    "name": "Thiru Notes",
    "description": "A beautiful note taking app.",
    "iconUrl": "https://...",
    "playStoreUrl": "https://play.google.com/..."
  }
]
```
