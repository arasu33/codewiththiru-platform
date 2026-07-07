package com.codewiththiru.platform.updates.api

/**
 * Defines the type of update being requested or handled.
 */
enum class UpdateType {
    /**
     * A background update that allows the user to continue using the app.
     */
    Flexible,

    /**
     * A blocking update that requires the user to wait until it finishes.
     */
    Immediate,

    /**
     * A strict blocking update managed potentially outside of Play Store flows or as a strict hard-block.
     */
    Force,

    /**
     * Not an actual app update, just presenting the release notes of the current version.
     */
    WhatsNewOnly,
}
