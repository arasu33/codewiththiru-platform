package com.codewiththiru.platform.developer.inspection

/**
 * Asserts thread preconditions in critical platform sections.
 */
object ThreadInspector {
    fun assertNotMainThread() {
        val threadName = Thread.currentThread().name
        if (threadName.contains("main", ignoreCase = true)) {
            throw IllegalStateException("Blocking operation called on the main thread!")
        }
    }
}
