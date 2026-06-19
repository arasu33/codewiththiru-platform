package com.codewiththiru.platform.core.logger

/**
 * Configuration options for the platform logger.
 *
 * Exposes flags to toggle logging and establish severity thresholds.
 *
 * @property enabled Sets whether logging is enabled globally.
 * @property minLevel Configures the minimum log severity level that will be printed.
 */
data class CustLoggerConfig(
    val enabled: Boolean = true,
    val minLevel: CustLogLevel = CustLogLevel.INFO,
)
