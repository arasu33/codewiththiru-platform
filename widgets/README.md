# 🧩 Widgets Module (`:widgets`)

Higher-level, reusable presentation components built on top of `:designsystem`.

## Features
- **PlatformTopAppBar**: Standardized Material 3 top app bar with navigation back handling, title, optional subtitle, and actions (`ThiruTopAppBar` maintained as backward-compatible deprecated alias).
- **EmptyStateView**: Visual empty-state placeholder with illustration/icon, title, subtitle, and action button.
- **LoadingOverlay**: Full-screen or scoped transparent blocking loader preventing input during long-running tasks.
- **StandardBottomSheet**: Standardized Material 3 `ModalBottomSheet` with drag handle and proper insets.

## Quick Start
```kotlin
// 1. Dependency
implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))
implementation("com.codewiththiru.platform:widgets")

// 2. Use in Scaffold
Scaffold(
    topBar = {
        PlatformTopAppBar(
            title = "My Library",
            subtitle = "12 Items",
            navigationIcon = Icons.Default.ArrowBack,
            onNavigationIconClick = { navController.popBackStack() }
        )
    }
) { padding ->
    if (items.isEmpty()) {
        EmptyStateView(
            title = "No decks yet",
            description = "Create your first flashcard deck to begin studying.",
            actionLabel = "Create Deck",
            onActionClick = { /* open create dialog */ }
        )
    }
}
```
