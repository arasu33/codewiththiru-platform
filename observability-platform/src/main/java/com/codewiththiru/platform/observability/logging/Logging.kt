package com.codewiththiru.platform.observability.logging

public enum class LogLevel {
    VERBOSE, DEBUG, INFO, WARN, ERROR, FATAL
}

public data class LogEvent(
    val timestamp: Long,
    val level: LogLevel,
    val tag: String,
    val message: String,
    val throwable: Throwable? = null,
    val metadata: Map<String, String> = emptyMap()
)

public interface LogFormatter {
    public fun format(event: LogEvent): String
}

public interface LogDestination {
    public suspend fun write(event: LogEvent)
}

public data class LogPolicy(
    val minimumLevel: LogLevel = LogLevel.DEBUG,
    val maskPii: Boolean = true,
    val offlineBuffering: Boolean = true
)

public interface Logger {
    public fun v(tag: String, message: String, throwable: Throwable? = null, metadata: Map<String, String> = emptyMap())
    public fun d(tag: String, message: String, throwable: Throwable? = null, metadata: Map<String, String> = emptyMap())
    public fun i(tag: String, message: String, throwable: Throwable? = null, metadata: Map<String, String> = emptyMap())
    public fun w(tag: String, message: String, throwable: Throwable? = null, metadata: Map<String, String> = emptyMap())
    public fun e(tag: String, message: String, throwable: Throwable? = null, metadata: Map<String, String> = emptyMap())
    public fun fatal(tag: String, message: String, throwable: Throwable? = null, metadata: Map<String, String> = emptyMap())
}
