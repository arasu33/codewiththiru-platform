# Feedback API Guide

## 1. Configuration
The Feedback module is strictly controlled via `FeedbackConfig`. You must build a config before launching the UI.

```kotlin
val config = FeedbackConfig.Builder()
    .setCategories(listOf(FeedbackCategory("bug", "Bug Report")))
    .setPrivacyConfig(FeedbackPrivacyConfig(redactPii = true))
    .setDraftStorageConfig(DraftStorageConfig(encrypted = true))
    .build()
```

## 2. Implementing Providers
You must provide a `FeedbackSubmissionProvider` to handle the actual API call or email intent.

```kotlin
class MySubmissionProvider(private val api: ApiClient) : FeedbackSubmissionProvider {
    override suspend fun submit(payload: FeedbackPayload): FeedbackSubmissionResult {
        return try {
            api.sendFeedback(payload)
            FeedbackSubmissionResult.Success
        } catch (e: Exception) {
            FeedbackSubmissionResult.NetworkError(retryAvailable = true)
        }
    }
}
```

## 3. UI Integration
Pass your dependencies into `FeedbackScreen`. Note that `FeedbackScreen` is strictly decoupled; it does not instantiate its own `ViewModel`. You must hoist the state.

```kotlin
@Composable
fun FeedbackRoute(viewModel: FeedbackViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    
    FeedbackScreen(
        uiState = uiState,
        formState = formState,
        config = myConfig,
        onAction = viewModel::onAction
    )
}
```
