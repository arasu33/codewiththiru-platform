# Rating Module Event Catalog

This catalog documents all analytic events emitted by the `:rating` module via the `RatingAnalyticsProvider`.

## `RatingAnalyticsEvent`

| Event Name | Description |
|---|---|
| `PromptShown` | Emitted when the initial rating prompt (e.g., Dialog or BottomSheet) is presented to the user. |
| `PromptDismissed` | Emitted when the user explicitly dismisses the prompt without interacting (e.g., clicking outside or pressing "Not Now"). |
| `StarSelected` | Emitted when the user taps a star rating. The star value (1-5) is included in the event payload. |
| `SubmitClicked` | Emitted when the user confirms their star rating by clicking the submit button. |
| `FeedbackRedirected` | Emitted when a user submits a rating below the configured `playReviewThreshold` and is redirected to the internal feedback flow. |
| `ReviewLaunched` | Emitted when a user submits a rating equal to or above the configured `playReviewThreshold` and the Google Play In-App Review API is invoked. |
| `ReviewLaunchFailed` | Emitted when the Play Core API throws an exception or is unavailable. |

## `RatingTriggerSource`

Events are typically tagged with a `RatingTriggerSource` to identify *why* the prompt was shown:

- `AppLaunch`: Prompted upon a fresh application start after meeting rules.
- `SignificantEvent`: Prompted explicitly after the user achieved a milestone.
- `Settings`: Prompted because the user explicitly clicked "Rate App" in the settings menu.
- `PushNotification`: (Future) Prompted via a remote push campaign.
