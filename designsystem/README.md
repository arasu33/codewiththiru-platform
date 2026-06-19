# Design System

The `:designsystem` module contains the central, reusable UI components and theme foundation (Material 3) for the `codewiththiru-platform`.

## Features
- Dynamic Color Support (Android 12+)
- Dark/Light Theme Switching
- High-fidelity Typography and Shape Tokens
- Strict Accessibility Defaults

## Components
- **CustText**: Wrapper around Material 3 Text supporting Strings and AnnotatedStrings.
- **CustButton**: Filled, Outlined, and Text variants with built-in accessibility and a `loading` state.
- **CustCard**: Filled, Outlined, and Elevated variants with separate clickable and non-clickable APIs.
- **CustTextField**: Accessible wrapper with internal error and helper text mapping.
- **CustTopBar**: Standard Material 3 TopAppBar with an embedded subtitle slot.
- **CustCenterAlignedTopBar**: Center-aligned Material 3 TopAppBar variant.
- **CustDialog**: Flexible slot-based wrapper over Material 3 AlertDialog.
- **CustAlertDialog**: Convenience wrapper for typical text-and-action confirmation dialogs.

Please see [API_GUIDE.md](API_GUIDE.md) for usage examples.
