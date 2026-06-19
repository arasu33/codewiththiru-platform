package com.codewiththiru.platform.core.logger

/**
 * Interface defining a pluggable logging output engine.
 *
 * Implementations of this interface process and write formatted logs to their respective destinations.
 */
interface CustLogPrinter {
    /**
     * Prints a log message.
     *
     * @param level The log severity level.
     * @param tag The tag/context identifier of the log source.
     * @param message The main log body description.
     * @param throwable An optional exception/throwable stack trace.
     */
    fun printLog(
        level: CustLogLevel,
        tag: String,
        message: String,
        throwable: Throwable? = null,
    )
}
