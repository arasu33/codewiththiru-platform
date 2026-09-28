# 📈 Enterprise Growth Platform (`:growth-platform`)

Comprehensive growth, viral referral mechanics, experimentation (A/B testing), and user retention engine.

---

## 🚀 Features

- **Referral & Invites (`InviteManager`)**: Generate cryptographic, personalized referral links with customizable base domains (`DefaultInviteManager(baseInviteUrl = "https://myapp.com/invite/")`).
- **A/B Experimentation (`ExperimentManager`)**: Dynamic variant bucketing, rollout percentages, and statistical cohort evaluation.
- **Engagement Workflows**: Coordinate cross-module retention campaigns with notifications and coupon incentives.

---

## 📦 Dependency Setup (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:growth-platform")
}
```

---

## 💡 Quick Start

```kotlin
// Initialize with your app's custom invite domain
val inviteManager = DefaultInviteManager(baseInviteUrl = "https://myapp.com/invite/")

// Generate a tracked referral link
val link = inviteManager.generateInviteLink(userId = "user_123", campaignId = "summer_promo")
println(link.url) // https://myapp.com/invite/user_123
```
