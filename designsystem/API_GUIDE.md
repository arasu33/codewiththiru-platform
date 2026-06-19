# API Guide

## Theme Initialization
Wrap your application or feature screens with `CustTheme`:

```kotlin
@Composable
fun App() {
    CustTheme(
        dynamicColor = true, // default is true
        darkTheme = isSystemInDarkTheme() // default extracts from system
    ) {
        // App Content
    }
}
```

## Tokens Access
While you should prefer `MaterialTheme.colorScheme` or `MaterialTheme.typography`, custom design properties can be accessed via composition locals:
```kotlin
val padding = LocalCustSpacing.current.medium
```

## Components

### CustText
```kotlin
CustText("Standard text")
CustText(annotatedString = buildAnnotatedString { append("Annotated") })
```

### CustButton
Supports a built-in `loading` state that intercepts clicks and handles accessibility semantics.
```kotlin
CustButton(onClick = { /* action */ }, loading = isLoading) {
    CustText("Submit")
}
CustOutlinedButton(onClick = { }) { CustText("Cancel") }
CustTextButton(onClick = { }) { CustText("Skip") }
```

### CustCard
Strict separation between clickable and non-clickable cards to avoid unnecessary semantics.
```kotlin
CustCard { CustText("Static content") }
CustCard(onClick = { /* navigate */ }) { CustText("Interactive card") }
```

### CustTextField
```kotlin
CustTextField(
    value = "Sample",
    onValueChange = {},
    helperText = "Helper text",
    maxLength = 20
)
```

### CustPasswordTextField
Automatically handles password visibility state.
```kotlin
CustPasswordTextField(
    value = "password123",
    onValueChange = {},
    initiallyVisible = false
)
```

### CustTopBar
Top App bars natively support typography scaling and custom subtitle structures out-of-the-box.
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo() {
    CustTopBar(
        title = { CustText("Home") },
        subtitle = { CustText("Online") },
        navigationIcon = {
            CustButton(onClick = {}) { CustText("Back") }
        }
    )
}
```

### CustAlertDialog
A simple wrapper over `CustDialog` for standard destructive/confirmation prompts.
```kotlin
CustAlertDialog(
    title = "Delete Account?",
    message = "This action cannot be undone.",
    confirmButtonText = "Delete",
    dismissButtonText = "Cancel",
    onConfirm = { /* ... */ },
    onDismiss = { /* ... */ },
    isDestructive = true
)
```

## Widgets

### CustLoading
Displays a loading indicator with an optional semantic message.
```kotlin
CustLoading(
    type = LoadingType.Circular,
    message = "Authenticating..."
)
```

### CustShimmer
Skeleton loading placeholder to mask data before rendering.
```kotlin
// Box variant
CustShimmerBox(
    modifier = Modifier.width(200.dp).height(24.dp)
)

// Modifier extension
Box(modifier = Modifier.custShimmer().fillMaxWidth().height(100.dp))
```

### CustEmptyState
Used to depict zero-data views clearly to the user.
```kotlin
CustEmptyState(
    title = "No Matches Found",
    message = "Try clearing your filters to see more results.",
    illustration = { CustAvatar(painter = null) }, // Use placeholder or icon here
    actionButton = {
        CustButton(onClick = { /* ... */ }) {
            CustText("Clear Filters")
        }
    }
)
```

### CustErrorState
Used for terminal or network errors requiring user intervention.
```kotlin
CustErrorState(
    title = "Network Timeout",
    message = "We couldn't reach the server.",
    errorCode = "504_GATEWAY",
    onRetry = { /* ... */ },
    retryText = "Try Again"
)
```

### CustBadge
Notification badging capable of max limit rendering.
```kotlin
CustBadge(count = 105, maxCount = 99) {
    Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
}
```

### CustAvatar
Robust avatar scaling without heavy image loading dependencies.
```kotlin
CustAvatar(
    initials = "John Doe",
    size = AvatarSize.Medium
)
```

### CustChip
Filtering and assisting actions.
```kotlin
CustFilterChip(
    label = "Active",
    selected = true,
    onClick = { /* ... */ }
)
```

### CustSectionHeader
Used to demarcate major areas in lists or settings. Automatically triggers `heading()` semantics for TalkBack.
```kotlin
CustSectionHeader(
    title = "Settings",
    subtitle = "Version 1.0",
    action = { CustButton(onClick = {}) { CustText("Edit") } }
)
```

### CustInfoRow
Versatile row mapper for settings and lists.
```kotlin
CustInfoRow(
    label = "Privacy Policy",
    value = "Updated",
    onClick = { /* Navigates */ },
    trailingContent = { Icon(Icons.Default.KeyboardArrowRight, null) }
)
```
