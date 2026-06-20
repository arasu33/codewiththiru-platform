# API Guide

## 1. Launching the Entry UI
Integrate the `CouponEntryScreen` into your Compose navigation tree:
```kotlin
CouponEntryScreen(
    viewModel = hiltViewModel<CouponViewModel>(),
    onNavigateBack = { navController.popBackStack() },
    onLaunchReward = { reward -> handleReward(reward) }
)
```

## 2. Setting Policies
Inject your `CouponRedemptionPolicy`:
```kotlin
val policy = CouponRedemptionPolicy(
    allowMultipleRedemptions = false,
    allowOfflineRedemption = true,
    maxRedemptionsPerUser = 1
)
```

## 3. Implementing Feature Gates
Extend `CouponFeatureGate` to unlock content:
```kotlin
class PremiumFeatureGate : CouponFeatureGate {
    override suspend fun unlock(reward: CouponReward) {
        if (reward is CouponReward.PremiumUnlock) {
            premiumManager.grantAccess(reward.durationDays)
        }
    }
}
```
