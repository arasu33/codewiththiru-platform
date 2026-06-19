package com.codewiththiru.platform.core.logger

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class CustLoggerTest {
    private class FakeLogPrinter : CustLogPrinter {
        val printedLogs = CopyOnWriteArrayList<LogEntry>()

        override fun printLog(
            level: CustLogLevel,
            tag: String,
            message: String,
            throwable: Throwable?,
        ) {
            printedLogs.add(LogEntry(level, tag, message, throwable))
        }
    }

    private data class LogEntry(
        val level: CustLogLevel,
        val tag: String,
        val message: String,
        val throwable: Throwable?,
    )

    private val printerA = FakeLogPrinter()
    private val printerB = FakeLogPrinter()

    @Before
    fun setUp() {
        printerA.printedLogs.clear()
        printerB.printedLogs.clear()
        CustLogger.resetToDefaults()
    }

    @Test
    fun testInitializationAndPrinters() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.DEBUG),
            printers = listOf(printerA, printerB),
        )

        CustLogger.d("TEST", "Debug message")

        assertEquals(1, printerA.printedLogs.size)
        assertEquals(1, printerB.printedLogs.size)

        val entryA = printerA.printedLogs[0]
        assertEquals(CustLogLevel.DEBUG, entryA.level)
        assertEquals("TEST", entryA.tag)
        assertEquals("Debug message", entryA.message)
    }

    @Test
    fun testLogLevelFiltering() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.WARN),
            printers = listOf(printerA),
        )

        CustLogger.d("TEST", "Debug message") // should be filtered out
        CustLogger.i("TEST", "Info message") // should be filtered out
        CustLogger.w("TEST", "Warning") // should be printed
        CustLogger.e("TEST", "Error") // should be printed

        assertEquals(2, printerA.printedLogs.size)
        assertEquals(CustLogLevel.WARN, printerA.printedLogs[0].level)
        assertEquals(CustLogLevel.ERROR, printerA.printedLogs[1].level)
    }

    @Test
    fun testDisabledLogger() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = false, minLevel = CustLogLevel.VERBOSE),
            printers = listOf(printerA),
        )

        CustLogger.e("TEST", "Fatal error") // should be ignored because enabled is false

        assertTrue(printerA.printedLogs.isEmpty())
    }

    @Test
    fun testDynamicPrinterRegistration() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.VERBOSE),
            printers = listOf(printerA),
        )

        CustLogger.v("TEST", "Log 1")

        CustLogger.addPrinter(printerB)
        CustLogger.v("TEST", "Log 2")

        CustLogger.removePrinter(printerA)
        CustLogger.v("TEST", "Log 3")

        // printerA received Log 1 and Log 2
        assertEquals(2, printerA.printedLogs.size)
        assertEquals("Log 1", printerA.printedLogs[0].message)
        assertEquals("Log 2", printerA.printedLogs[1].message)

        // printerB received Log 2 and Log 3
        assertEquals(2, printerB.printedLogs.size)
        assertEquals("Log 2", printerB.printedLogs[0].message)
        assertEquals("Log 3", printerB.printedLogs[1].message)
    }

    @Test
    fun testLazyLoggingEvaluation() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.WARN),
            printers = listOf(printerA),
        )

        var evaluated = false

        // 1. Should NOT evaluate lambda when log level is below threshold
        CustLogger.d("TEST") {
            evaluated = true
            "Lazy Debug message"
        }
        assertFalse(evaluated)
        assertTrue(printerA.printedLogs.isEmpty())

        // 2. Should evaluate lambda when log level is at or above threshold
        CustLogger.w("TEST") {
            evaluated = true
            "Lazy Warning message"
        }
        assertTrue(evaluated)
        assertEquals(1, printerA.printedLogs.size)
        assertEquals("Lazy Warning message", printerA.printedLogs[0].message)
    }

    @Test
    fun testAllLazyLoggingVariants() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.INFO),
            printers = listOf(printerA),
        )

        var debugEvaluated = false
        var infoEvaluated = false
        var warnEvaluated = false
        var errorEvaluated = false

        CustLogger.d("TEST") {
            debugEvaluated = true
            "Debug"
        }
        CustLogger.i("TEST") {
            infoEvaluated = true
            "Info"
        }
        CustLogger.w("TEST") {
            warnEvaluated = true
            "Warn"
        }
        CustLogger.e("TEST") {
            errorEvaluated = true
            "Error"
        }

        // Under INFO log level:
        // debug should NOT evaluate
        assertFalse(debugEvaluated)
        // info, warn, error SHOULD evaluate
        assertTrue(infoEvaluated)
        assertTrue(warnEvaluated)
        assertTrue(errorEvaluated)

        assertEquals(3, printerA.printedLogs.size)
        assertEquals(CustLogLevel.INFO, printerA.printedLogs[0].level)
        assertEquals(CustLogLevel.WARN, printerA.printedLogs[1].level)
        assertEquals(CustLogLevel.ERROR, printerA.printedLogs[2].level)
    }

    @Test
    fun testResetToDefaults() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.VERBOSE),
            printers = listOf(printerA, printerB),
        )

        CustLogger.resetToDefaults()

        val state = CustLogger.state.get()
        assertEquals(CustLogLevel.INFO, state.config.minLevel)
        assertEquals(1, state.printers.size)
        assertTrue(state.printers[0] is ConsoleLogPrinter)
    }

    @Test
    fun testThreadSafety() {
        CustLogger.initialize(
            config = CustLoggerConfig(enabled = true, minLevel = CustLogLevel.VERBOSE),
            printers = listOf(printerA),
        )

        val threadCount = 10
        val operationsPerThread = 100
        val executor = Executors.newFixedThreadPool(threadCount)
        val latch = CountDownLatch(threadCount)

        for (i in 0 until threadCount) {
            executor.submit {
                try {
                    for (j in 0 until operationsPerThread) {
                        CustLogger.addPrinter(printerB)
                        CustLogger.d("CONCURRENT", "Msg $j")
                        CustLogger.removePrinter(printerB)
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executor.shutdown()

        // Verify that the concurrent operations executed cleanly without exceptions.
        assertFalse(printerA.printedLogs.isEmpty())
    }
}
