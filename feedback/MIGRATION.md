# Migration Guide

## Upgrading to 0.3.0

### `FeedbackCategory` Migration
`FeedbackCategory` is no longer an Enum. You must migrate your enum definitions to dynamic instances.

**Before:**
```kotlin
configBuilder.setCategories(listOf(FeedbackCategory.BUG_REPORT))
```

**After:**
```kotlin
configBuilder.setCategories(listOf(FeedbackCategory("bug", "Bug Report")))
```

### State Management & UI Binding
`FeedbackScreen` no longer accepts `submissionProvider` directly. It now relies on a hoisted `FeedbackViewModel`.

**Before:**
```kotlin
FeedbackScreen(
    submissionProvider = myProvider,
    eventListener = listener
)
```

**After:**
```kotlin
FeedbackScreen(
    uiState = viewModel.uiState.collectAsState().value,
    formState = viewModel.formState.collectAsState().value,
    config = myConfig,
    onAction = viewModel::onAction
)
```

### Encrypted Drafts Migration
Migration from plaintext `DataStore` to `EncryptedSharedPreferences` happens automatically upon instantiation of `FeedbackDraftProviderImpl` if `migratePlaintext` is set to `true` (default). No manual consumer migration is required.
