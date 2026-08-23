package com.codewiththiru.platform.core.logger

import java.util.concurrent.atomic.AtomicReference

/**
 * Thread-safe logging facade for the platform.
 *
 * Supports pluggable [CustLogPrinter] engines, dynamic updates to configurations,
 * and standard severity filtering. Uses atomic, lock-free state modifications.
 */
@Suppress("TooManyFunctions")
object CustLogger {
    @PublishedApi
    internal val state =
        AtomicReference(
            LoggerState(
                config = CustLoggerConfig(),
                printers = listOf(ConsoleLogPrinter()),
            ),
        )

    /**
     * Initializes the logger with a custom configuration and a list of output printers.
     *
     * @param config The logging configuration rules.
     * @param printers A list of active printers. Defaults to a single [ConsoleLogPrinter].
     */
    fun initialize(
        config: CustLoggerConfig,
        printers: List<CustLogPrinter> = listOf(ConsoleLogPrinter()),
    ) {
        state.set(LoggerState(config, printers))
    }

    /**
     * Dynamically adds a printer to the active printers list.
     *
     * @param printer The printer to register.
     */
    fun addPrinter(printer: CustLogPrinter) {
        var added = false
        while (!added) {
            val current = state.get()
            if (current.printers.contains(printer)) {
                added = true
            } else {
                val next = LoggerState(current.config, current.printers + printer)
                added = state.compareAndSet(current, next)
            }
        }
    }

    /**
     * Dynamically removes a printer from the active printers list.
     *
     * @param printer The printer to unregister.
     */
    fun removePrinter(printer: CustLogPrinter) {
        var removed = false
        while (!removed) {
            val current = state.get()
            if (!current.printers.contains(printer)) {
                removed = true
            } else {
                val next = LoggerState(current.config, current.printers - printer)
                removed = state.compareAndSet(current, next)
            }
        }
    }

    /**
     * Dynamically updates the logging configuration.
     *
     * @param config The new configuration options.
     */
    fun updateConfig(config: CustLoggerConfig) {
        var updated = false
        while (!updated) {
            val current = state.get()
            val next = LoggerState(config, current.printers)
            updated = state.compareAndSet(current, next)
        }
    }

    /**
     * Resets the logging configuration and active printers to default values.
     *
     * Note: This function is package-private (internal) and intended strictly for testing purposes.
     */
    internal fun resetToDefaults() {
        state.set(
            LoggerState(
                config = CustLoggerConfig(),
                printers = listOf(ConsoleLogPrinter()),
            ),
        )
    }

    /**
     * Checks whether a log level is permitted to print based on active configuration settings.
     *
     * @param level The log level to verify.
     * @return True if logging is enabled and the level is above the minimum configured severity.
     */
    @PublishedApi
    internal fun isLoggable(level: CustLogLevel): Boolean {
        val currentState = state.get()
        val config = currentState.config
        return config.enabled && level.priority >= config.minLevel.priority
    }

    /**
     * Prints a verbose-level log message.
     *
     * @param tag Logging category tag.
     * @param message Text content of the log.
     * @param throwable Optional exception stack trace.
     */
    fun v(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        log(CustLogLevel.VERBOSE, tag, message, throwable)
    }

    /**
     * Prints a debug-level log message.
     *
     * @param tag Logging category tag.
     * @param message Text content of the log.
     * @param throwable Optional exception stack trace.
     */
    fun d(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        log(CustLogLevel.DEBUG, tag, message, throwable)
    }

    /**
     * Prints an debug-level log message lazily.
     *
     * The [message] block is evaluated only if debug logging is enabled.
     *
     * @param tag Logging category tag.
     * @param throwable Optional exception stack trace.
     * @param message Lambda block producing the log body.
     */
    inline fun d(
        tag: String,
        throwable: Throwable? = null,
        crossinline message: () -> String,
    ) {
        if (isLoggable(CustLogLevel.DEBUG)) {
            log(CustLogLevel.DEBUG, tag, message(), throwable)
        }
    }

    /**
     * Prints an info-level log message.
     *
     * @param tag Logging category tag.
     * @param message Text content of the log.
     * @param throwable Optional exception stack trace.
     */
    fun i(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        log(CustLogLevel.INFO, tag, message, throwable)
    }

    /**
     * Prints an info-level log message lazily.
     *
     * The [message] block is evaluated only if info logging is enabled.
     *
     * @param tag Logging category tag.
     * @param throwable Optional exception stack trace.
     * @param message Lambda block producing the log body.
     */
    inline fun i(
        tag: String,
        throwable: Throwable? = null,
        crossinline message: () -> String,
    ) {
        if (isLoggable(CustLogLevel.INFO)) {
            log(CustLogLevel.INFO, tag, message(), throwable)
        }
    }

    /**
     * Prints a warning-level log message.
     *
     * @param tag Logging category tag.
     * @param message Text content of the log.
     * @param throwable Optional exception stack trace.
     */
    fun w(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        log(CustLogLevel.WARN, tag, message, throwable)
    }

    /**
     * Prints a warning-level log message lazily.
     *
     * The [message] block is evaluated only if warning logging is enabled.
     *
     * @param tag Logging category tag.
     * @param throwable Optional exception stack trace.
     * @param message Lambda block producing the log body.
     */
    inline fun w(
        tag: String,
        throwable: Throwable? = null,
        crossinline message: () -> String,
    ) {
        if (isLoggable(CustLogLevel.WARN)) {
            log(CustLogLevel.WARN, tag, message(), throwable)
        }
    }

    /**
     * Prints an error-level log message.
     *
     * @param tag Logging category tag.
     * @param message Text content of the log.
     * @param throwable Optional exception stack trace.
     */
    fun e(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        log(CustLogLevel.ERROR, tag, message, throwable)
    }

    /**
     * Prints an error-level log message lazily.
     *
     * The [message] block is evaluated only if error logging is enabled.
     *
     * @param tag Logging category tag.
     * @param throwable Optional exception stack trace.
     * @param message Lambda block producing the log body.
     */
    inline fun e(
        tag: String,
        throwable: Throwable? = null,
        crossinline message: () -> String,
    ) {
        if (isLoggable(CustLogLevel.ERROR)) {
            log(CustLogLevel.ERROR, tag, message(), throwable)
        }
    }

    /**
     * Main log dispatcher.
     *
     * Exposes package-private access to permit inline compilation checks.
     */
    @PublishedApi
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    internal fun log(
        level: CustLogLevel,
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        val currentState = state.get()
        val config = currentState.config
        if (!(config.enabled && level.priority >= config.minLevel.priority)) return
        currentState.printers.forEach { printer ->
            try {
                printer.printLog(level, tag, message, throwable)
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: kotlin.coroutines.cancellation.CancellationException) {
                throw e
            } catch (e: Exception) {
                // Ignore printer exceptions to prevent app crash
            }
        }
    }

    @PublishedApi
    internal class LoggerState(
        val config: CustLoggerConfig,
        val printers: List<CustLogPrinter>,
    )
}
