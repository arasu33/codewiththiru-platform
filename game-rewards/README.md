# Game Rewards Framework (`:game-rewards`)

A secure, offline-first framework managing wallets, inventories, multipliers, and complex claim policies for CodeWithThiru platform games.

## Features
- **Extensive Currency Types**: Built-in support for Coins, Stars, XP, Lives, Tickets, Energy, and more via `RewardType`.
- **Advanced Multipliers**: The `RewardMultiplierEngine` correctly stacks global multipliers, VIP bonuses, and weekend events multiplicatively by default.
- **Strict Policies**: Use `RewardPolicy` to strictly enforce "Max Lives = 5" caps or daily coin limits.
- **Claim Idempotency**: The `ClaimEngine` guarantees duplicate rewards aren't claimed maliciously by utilizing `idempotencyKey` tracking.
- **Inventory & Consumables**: `RewardInventory` tracks items like Boosters or Cosmetics securely.

## Integration
This module is pure Kotlin. Link the `RewardManager` to your app layer, and bind it to UI observers.

```kotlin
// Example: Granting a VIP login reward
val reward = RewardDefinition("login", RewardType.COINS, 100L, RewardSource.DAILY_LOGIN)
val success = rewardManager.grantReward(reward, idempotencyKey = "2026-06-27-login")

// The UI can reactively update
rewardManager.observeBalance(RewardType.COINS).collect { balance ->
    updateCoinsUI(balance.totalAmount)
}
```

## Testing
Run unit tests to verify multiplier stacking and policy constraints:
```bash
./gradlew :game-rewards:test
```
