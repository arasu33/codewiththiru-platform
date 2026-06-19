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
