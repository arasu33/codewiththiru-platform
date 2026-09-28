# 🎮 Gamification Platform (`:gamification`)

An offline-first, provider-agnostic, enterprise-grade gamification engine powering progression, rewards, and player retention.

---

## 🚀 Features

- **XP & Leveling System**: Dynamic level progression with custom XP calculation curves.
- **Achievements Platform**: Trigger, unlock, and track progress on tier-based achievements.
- **Streak Engine**: Daily login tracking with customizable freeze days and grace periods.
- **Reward Economy**: Virtual currencies (Coins, Gems) with transactional balances.
- **Security & Anti-Cheat**: Cryptographic offline signature validation to detect score/XP tampering.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:gamification")
}
```

---

## 📚 Documentation
For complete architectural details, API guides, and testing strategies, see [docs/README.md](docs/README.md).
