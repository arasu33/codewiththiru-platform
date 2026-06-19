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

## Widgets
- **CustLoading**: Configurable Circular and Linear progress indicators with optional text messaging support.
- **CustShimmer**: Animated skeleton loading placeholder modifier (`Modifier.custShimmer()`) and `CustShimmerBox()` component.
- **CustEmptyState**: Customizable empty state widget with integrated layout management for illustrations, titles, and actions.
- **CustErrorState**: Accessible error handling widget containing unified error codes, merged TalkBack semantic parsing, and retry actions.
- **CustBadge**: Notification and counter badging wrapping Material 3 bounds.
- **CustChip**: `CustAssistChip` and `CustFilterChip` variants.
- **CustAvatar**: `Image`, `Initials`, or `Placeholder` variants sized centrally via `AvatarSize`.
- **CustDivider**: `CustHorizontalDivider` and `CustVerticalDivider` semantic wrappers.
- **CustSectionHeader**: Accessible list-section titling with built in TalkBack headings.
- **CustInfoRow**: Flexible single-line information layouts mapping click states seamlessly.

Please see [API_GUIDE.md](API_GUIDE.md) for usage examples.
