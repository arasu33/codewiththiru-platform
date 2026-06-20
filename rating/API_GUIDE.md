# Rating API Guide

## 1. Configuration
The Rating Engine requires strict configuration parameters before launching.

```kotlin
val config = RatingConfig.Builder()
    .setTriggerRules(
        RatingTriggerRules(
            minimumAppLaunches = 5,
            minimumDaysInstalled = 3,
            requiredSignificantEvents = 1
        )
    )
    .setCooldownPolicy(
        RatingCooldownPolicy(
            daysAfterDismissal = 14,
            daysAfterPlayReviewLaunch = 90
        )
    )
    .setPlayReviewThreshold(5)
    .build()
```

## 2. Trigger Engine Evaluation
Before showing the UI, you should evaluate if the user is eligible. The engine interacts directly with the `RatingRepository` (DataStore).

```kotlin
val result = triggerEngine.evaluateEligibility(config.triggerRules, config.cooldownPolicy)

if (result is RatingEligibilityResult.Eligible) {
    // Show Rating UI
}
```

## 3. UI Integration
Pass your configuration and a hoisted `RatingViewModel` into `RatingScreen`. 

```kotlin
@Composable
fun RatingRoute(viewModel: RatingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Listen for effects (e.g., launching play review or feedback)
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RatingEffect.LaunchPlayReview -> {
                    // Call PlayReviewProvider here
                }
                is RatingEffect.RedirectToFeedback -> {
                    // Navigate to :feedback module
                }
                is RatingEffect.ClosePrompt -> {
                    // Hide UI
                }
            }
        }
    }
    
    RatingScreen(
        uiState = uiState,
        config = config,
        onAction = viewModel::onAction
    )
}
```
