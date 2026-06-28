package com.codewiththiru.platform.developer.benchmark.memory

/**
 * Utility to measure time taken for a block of code to execute.
 * (Allocation measurement requires JVM args, so we start with execution time).
 */
object AllocationHelpers {
    inline fun measureExecutionTime(block: () -> Unit): Long {
        val start = System.currentTimeMillis()
        block()
        return System.currentTimeMillis() - start
    }
}
