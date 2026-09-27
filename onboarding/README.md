# 🚀 Onboarding Module (`:onboarding`)

First-launch onboarding walkthrough carousel and completion tracking for Android apps.

## Features
- **Modern Carousel**: Jetpack Compose `HorizontalPager` with smooth animated dot indicators.
- **First-Launch Tracking**: `DataStoreOnboardingRepository` tracks whether the user completed or skipped the onboarding flow.
- **Customizable**: Supply custom titles, descriptions, vector icons, and badges.

## Quick Start
```kotlin
// 1. Dependency
implementation("com.codewiththiru.platform:onboarding")

// 2. Render Onboarding Screen
val pages = listOf(
    OnboardingPage("1", "Welcome to StudySnap", "AI-powered study notes and instant flashcards."),
    OnboardingPage("2", "Sync Everywhere", "Access your revision decks across all your devices.")
)

OnboardingScreen(
    pages = pages,
    onComplete = { navController.navigate("home") },
    onSkip = { navController.navigate("home") }
)
```
