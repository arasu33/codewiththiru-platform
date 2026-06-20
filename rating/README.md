# Rating Module

The `:rating` module is an enterprise-grade Rating Engine for the CodeWithThiru app ecosystem. It operates seamlessly without forcing internet permissions and strictly adheres to Google Play In-App Review guidelines.

## Features
- **Play Review Integration**: Automates fetching and launching the `ReviewInfo` bottom sheet from Play Core.
- **Behavioral Prompts**: Only triggers when specific engagement thresholds (Launches, Events, Days Installed) are met, maximizing positive sentiment.
- **Negative Feedback Redirection**: Allows implicit redirection to the `:feedback` module or custom flows if the user selects 1-4 stars, protecting the app's Play Store rating.
- **MVI Architecture**: State is completely decoupled from UI, allowing `RatingScreen` to be swapped or styled (`Dialog`, `BottomSheet`, `Fullscreen`).
- **DataStore Cooldowns**: Persists prompt dates and launch counts locally to ensure users aren't spammed with rating requests.

## Setup
See [API_GUIDE.md](API_GUIDE.md) for full integration instructions.
