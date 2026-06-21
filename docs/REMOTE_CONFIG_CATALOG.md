# Remote Config Catalog

The CodeWithThiru Platform utilizes Firebase Remote Config and custom server configurations to control feature rollouts, UI tweaks, and monetization logic dynamically. This is an exhaustive list of all available configuration keys.

## Naming Conventions
*   `feature_*`: Toggles for enabling/disabling entirely new capabilities.
*   `ui_*`: Values controlling the visual presentation.
*   `ads_*`: Configuration specific to monetization frequency and placement.
*   `sys_*`: System-level overrides and update parameters.

## Feature Toggles
| Key Name | Type | Default Value | Description | Module |
| :--- | :--- | :--- | :--- | :--- |
| `feature_onboarding_v2_enabled` | Boolean | `false` | Enables the redesigned onboarding flow. | Core |
| `feature_premium_tier_enabled` | Boolean | `true` | Exposes the premium subscription UI to users. | Billing |
| `feature_dark_mode_forced` | Boolean | `false` | Forces the app to ignore system theme and use dark mode. | Core |

## Monetization (Ads) Parameters
| Key Name | Type | Default Value | Description | Module |
| :--- | :--- | :--- | :--- | :--- |
| `ads_interstitial_interval_sec` | Integer | `120` | Minimum seconds between interstitial ad displays. | Ads |
| `ads_app_open_enabled` | Boolean | `true` | Enables ads displayed immediately upon app cold start. | Ads |
| `ads_native_feed_frequency` | Integer | `5` | Number of items in a list before inserting a native ad. | Ads |
| `ads_waterfall_timeout_ms` | Integer | `5000` | Timeout in milliseconds when attempting to load a specific ad network. | Ads |

## UI & Branding Configuration
| Key Name | Type | Default Value | Description | Module |
| :--- | :--- | :--- | :--- | :--- |
| `ui_primary_color_hex` | String | `#3F51B5` | Primary branding color. Applied dynamically to Compose themes. | Core |
| `ui_paywall_headline` | String | `"Unlock Pro Features!"`| Primary text displayed on the subscription purchase screen. | Billing |
| `ui_support_email` | String | `"support@codewiththiru.com"`| Email address used in the settings / contact us sections. | Core |

## System & Maintenance
| Key Name | Type | Default Value | Description | Module |
| :--- | :--- | :--- | :--- | :--- |
| `sys_force_update_version` | Integer | `0` | If local `versionCode` is `<` this value, triggers a blocking update dialog. | Core |
| `sys_maintenance_mode` | Boolean | `false` | Blocks all app functionality and shows a "Maintenance in progress" screen. | Core |
| `sys_api_base_url` | String | `"https://api.codewiththiru.com/v1/"` | Base endpoint for backend communication. | Core |
