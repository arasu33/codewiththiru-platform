package com.codewiththiru.platform.observability.crash

public data class CrashEvent(
    val id: String,
    val timestamp: Long,
    val exceptionType: String,
    val exceptionMessage: String,
    val stackTrace: String,
    val isFatal: Boolean,
    val threadName: String,
    val appBackgrounded: Boolean
)

public data class CrashSession(
    val sessionId: String,
    val startTime: Long,
    val breadcrumbs: List<String>
)

public data class CrashPolicy(
    val deduplicateIdenticalCrashes: Boolean = true,
    val attachLogcat: Boolean = true,
    val maxBreadcrumbs: Int = 100
)

public interface CrashReporter {
    public fun logNonFatal(throwable: Throwable, metadata: Map<String, String> = emptyMap())
    public fun recordBreadcrumb(message: String)
    public fun setUserId(userId: String)
    public fun installFatalCrashHandler()
}
