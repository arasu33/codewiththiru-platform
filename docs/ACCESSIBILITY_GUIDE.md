# Accessibility Guide

The CodeWithThiru Platform is committed to providing an inclusive experience for all users. This guide details how to implement accessibility best practices in Jetpack Compose to meet WCAG AA/AAA standards.

## TalkBack Support

TalkBack is Android's screen reader. All Compose UI must be fully navigable and comprehensible using TalkBack.

### Semantics Properties

Use the `semantics` modifier to provide meaningful descriptions and structural context to UI elements.

```kotlin
Image(
    painter = painterResource(id = R.drawable.ic_profile),
    contentDescription = stringResource(R.string.cd_user_profile), // Required for actionable images
    modifier = Modifier.clickable { /* action */ }
)
```

**Rule of Thumb:** If an element is purely decorative, pass `null` to `contentDescription`. If it conveys meaning or is interactive, provide a concise, localized description.

### Merging Descendants

When a single logical interactive element comprises multiple UI nodes (e.g., a card with an icon and text), merge their semantics so TalkBack treats them as a single focusable entity.

```kotlin
Row(
    modifier = Modifier
        .clickable(onClick = onArticleClick)
        .semantics(mergeDescendants = true) {} // Merges child semantics
) {
    Icon(Icons.Default.Article, contentDescription = null)
    Text("Read the latest news")
}
```

### Custom Actions

For complex elements, replace standard click semantics with custom actions to give users more context about what the action will do.

```kotlin
Modifier.semantics {
    customActions = listOf(
        CustomAccessibilityAction("Bookmark this article") {
            onBookmark()
            true
        }
    )
}
```

## Contrast Ratios

Colors must meet WCAG 2.1 AA standards:
*   **Normal Text:** Minimum contrast ratio of 4.5:1.
*   **Large Text (18pt+ or 14pt+ bold):** Minimum contrast ratio of 3.0:1.
*   **UI Components:** Meaningful UI elements (icons, borders) require a 3.0:1 ratio against their background.

The platform's Material 3 theme is pre-configured with accessible color pairs (e.g., `primary` vs `onPrimary`). Do not hardcode custom hex colors unless verified with an accessibility contrast checker.

## Touch Target Sizes

According to Material Design guidelines, all clickable targets must be at least **48x48 dp**.

Jetpack Compose automatically enforces this for built-in components (like `Button`, `IconButton`). If creating custom interactive elements, ensure the minimum size is met:

```kotlin
Box(
    modifier = Modifier
        .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
        .clickable { onClick() }
)
```

## State Announcement

When the state of a screen changes dynamically (e.g., loading finished, error occurred), use `LiveRegionMode` or `clearAndSetSemantics` to notify TalkBack users.

```kotlin
Text(
    text = "Payment Successful",
    modifier = Modifier.semantics {
        liveRegion = LiveRegionMode.Polite
    }
)
```

## Accessibility Testing

*   **Manual Testing:** Turn on TalkBack on a physical device and navigate the app with your eyes closed.
*   **Automated Testing:** Use the Accessibility Scanner app provided by Google.
*   **Compose Tests:** Unit tests can verify semantics properties using `onNodeWithContentDescription()` and `assertHasClickAction()`.
