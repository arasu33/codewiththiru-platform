package com.codewiththiru.platform.developer.testutils

/**
 * A logger that stores logs in a list for assertions rather than printing to Logcat/Console.
 */
class FakeLogger {
    val messages = java.util.concurrent.CopyOnWriteArrayList<String>()

    fun log(message: String) {
        messages.add(message)
    }

    fun clear() {
        messages.clear()
    }
}
