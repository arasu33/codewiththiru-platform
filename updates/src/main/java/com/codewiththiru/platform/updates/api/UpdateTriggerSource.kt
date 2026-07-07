package com.codewiththiru.platform.updates.api

/**
 * Defines the context in which an update check was triggered, useful for analytics.
 */
enum class UpdateTriggerSource {
    AppLaunch,
    Resume,
    Settings,
    ManualCheck,
    BackgroundSync,
}
