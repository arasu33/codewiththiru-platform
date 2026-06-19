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
