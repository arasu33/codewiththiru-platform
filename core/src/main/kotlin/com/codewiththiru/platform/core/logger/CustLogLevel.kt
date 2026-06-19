package com.codewiththiru.platform.core.logger

/**
 * Represents the log severity levels for the platform logger.
 *
 * Each log level contains a priority integer matching standard logging conventions,
 * enabling flexible log filtering based on a minimum level threshold.
 *
 * @property priority Integer representing the severity weight. Higher values denote higher severity.
 */
@Suppress("MagicNumber")
enum class CustLogLevel(val priority: Int) {
    /** Detailed and high-volume diagnostic information. */
    VERBOSE(2),

    /** Informational events useful during local debugging. */
    DEBUG(3),

    /** Significant administrative or operational events. */
    INFO(4),

    /** Potential anomalies or warnings that do not stop application execution. */
    WARN(5),

    /** Severe errors or failures that require immediate attention. */
    ERROR(6),

    /** Disables all logging completely. */
    NONE(7),
}
