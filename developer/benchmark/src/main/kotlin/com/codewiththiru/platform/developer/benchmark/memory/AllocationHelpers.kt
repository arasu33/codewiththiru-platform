package com.codewiththiru.platform.developer.benchmark.memory

/**
 * Utility to measure time taken for a block of code to execute.
 * (Allocation measurement requires JVM args, so we start with execution time).
 */
object AllocationHelpers {
    const val NANOS_PER_MILLISECOND = 1_000_000L

    inline fun measureExecutionTime(block: () -> Unit): Long {
        val start = System.nanoTime()
        block()
        return (System.nanoTime() - start) / NANOS_PER_MILLISECOND
    }
}
