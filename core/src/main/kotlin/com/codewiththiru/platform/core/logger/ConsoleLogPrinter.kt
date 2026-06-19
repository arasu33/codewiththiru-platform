package com.codewiththiru.platform.core.logger

import java.time.Instant

/**
 * A standard JVM console implementation of [CustLogPrinter].
 *
 * Formats log messages with UTC timestamps and prints them directly to standard output.
 */
class ConsoleLogPrinter : CustLogPrinter {
    override fun printLog(
        level: CustLogLevel,
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        val timestamp = Instant.now().toString()
        val exceptionStr = throwable?.let { "\n${it.stackTraceToString()}" } ?: ""
        println("[$timestamp] [${level.name}] [$tag]: $message$exceptionStr")
    }
}
