package com.codewiththiru.platform.developer.testutils

/**
 * A logger that stores logs in a list for assertions rather than printing to Logcat/Console.
 */
class FakeLogger {
    private val _messages = java.util.concurrent.CopyOnWriteArrayList<String>()
    val messages: List<String> get() = _messages

    fun log(message: String) {
        _messages.add(message)
    }

    fun clear() {
        _messages.clear()
    }
}
