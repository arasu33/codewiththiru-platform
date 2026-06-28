package com.codewiththiru.platform.developer.benchmark

import com.codewiththiru.platform.developer.benchmark.memory.AllocationHelpers
import org.junit.Assert.assertTrue
import org.junit.Test

class BenchmarkTest {
    @Test
    fun `test execution time measurement`() {
        val timeMs =
            AllocationHelpers.measureExecutionTime {
                Thread.sleep(10)
            }
        assertTrue(timeMs >= 10)
    }
}
