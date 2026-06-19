package com.codewiththiru.platform.core.result

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.cancellation.CancellationException

class CustResultTest {
    @Test
    fun testSuccessMonadFlow() {
        val result: CustResult<Int> = CustResult.Success(10)

        // Test Map
        val mapped = result.map { it * 2 }
        assertTrue(mapped is CustResult.Success)
        assertEquals(20, (mapped as CustResult.Success).value)

        // Test FlatMap
        val flatMapped = result.flatMap { CustResult.Success("Value is $it") }
        assertTrue(flatMapped is CustResult.Success)
        assertEquals("Value is 10", (flatMapped as CustResult.Success).value)

        // Test Fold
        val folded =
            result.fold(
                onSuccess = { "Success: $it" },
                onFailure = { "Failure: ${it.message}" },
            )
        assertEquals("Success: 10", folded)

        // Test onSuccess callback
        val successCalled = AtomicBoolean(false)
        result.onSuccess {
            assertEquals(10, it)
            successCalled.set(true)
        }
        assertTrue(successCalled.get())

        // Test onFailure callback (should not be executed)
        result.onFailure {
            fail("onFailure should not be executed for Success")
        }

        // Test getOrElse
        val value = result.getOrElse { 0 }
        assertEquals(10, value)
    }

    @Test
    fun testFailureMonadFlow() {
        val exception = RuntimeException("Operational failure")
        val result: CustResult<Int> = CustResult.Failure(exception)

        // Test Map (should pass through failure)
        val mapped = result.map { it * 2 }
        assertTrue(mapped is CustResult.Failure)
        assertEquals(exception, (mapped as CustResult.Failure).exception)

        // Test FlatMap (should pass through failure)
        val flatMapped = result.flatMap { CustResult.Success("Value is $it") }
        assertTrue(flatMapped is CustResult.Failure)
        assertEquals(exception, (flatMapped as CustResult.Failure).exception)

        // Test Fold
        val folded =
            result.fold(
                onSuccess = { "Success: $it" },
                onFailure = { "Failure: ${it.message}" },
            )
        assertEquals("Failure: Operational failure", folded)

        // Test onSuccess callback (should not be executed)
        result.onSuccess {
            fail("onSuccess should not be executed for Failure")
        }

        // Test onFailure callback
        val failureCalled = AtomicBoolean(false)
        result.onFailure {
            assertEquals(exception, it)
            failureCalled.set(true)
        }
        assertTrue(failureCalled.get())

        // Test getOrElse
        val value = result.getOrElse { 5 }
        assertEquals(5, value)

        // Test Recover
        val recovered = result.recover { 100 }
        assertTrue(recovered is CustResult.Success)
        assertEquals(100, (recovered as CustResult.Success).value)
    }

    @Test
    fun testCustRunCatchingSuccess() {
        val result =
            custRunCatching {
                "Valid data"
            }
        assertTrue(result is CustResult.Success)
        assertEquals("Valid data", (result as CustResult.Success).value)
    }

    @Test
    fun testCustRunCatchingFailure() {
        val result =
            custRunCatching {
                throw IllegalArgumentException("Invalid argument")
            }
        assertTrue(result is CustResult.Failure)
        val exception = (result as CustResult.Failure).exception
        assertTrue(exception is IllegalArgumentException)
        assertEquals("Invalid argument", exception.message)
    }

    @Test
    fun testCustRunCatchingCancellationException() {
        // Verification: CancellationException must not be caught and must be propagated.
        try {
            custRunCatching {
                throw CancellationException("Coroutine cancelled")
            }
            fail("CancellationException should have been thrown")
        } catch (e: Throwable) {
            assertTrue(e is CancellationException)
            assertEquals("Coroutine cancelled", e.message)
        }
    }

    @Test
    fun testCustRunCatchingFatalJvmErrorOutOfMemory() {
        // Verification: OutOfMemoryError (subclass of Error) must not be caught and must propagate.
        try {
            custRunCatching {
                throw OutOfMemoryError("JVM out of memory")
            }
            fail("OutOfMemoryError should have been thrown")
        } catch (e: Throwable) {
            assertTrue(e is OutOfMemoryError)
            assertEquals("JVM out of memory", e.message)
        }
    }

    @Test
    fun testCustRunCatchingFatalJvmErrorStackOverflow() {
        // Verification: StackOverflowError (subclass of Error) must not be caught and must propagate.
        try {
            custRunCatching {
                throw StackOverflowError("JVM stack overflow")
            }
            fail("StackOverflowError should have been thrown")
        } catch (e: Throwable) {
            assertTrue(e is StackOverflowError)
            assertEquals("JVM stack overflow", e.message)
        }
    }

    @Test
    fun testCustRunCatchingFatalJvmErrorVirtualMachineError() {
        // Verification: VirtualMachineError must not be caught and must propagate.
        try {
            custRunCatching {
                throw object : VirtualMachineError("Generic VM error") {}
            }
            fail("VirtualMachineError should have been thrown")
        } catch (e: Throwable) {
            assertTrue(e is VirtualMachineError)
            assertEquals("Generic VM error", e.message)
        }
    }
}
