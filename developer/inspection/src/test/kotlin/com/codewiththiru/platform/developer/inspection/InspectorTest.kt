package com.codewiththiru.platform.developer.inspection

import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.concurrent.thread

class InspectorTest {
    @Test
    fun `test thread inspector on separate thread`() {
        // This should pass because we are not naming this thread "main"
        thread(name = "background-thread") {
            ThreadInspector.assertNotMainThread()
        }.join()
    }

    @Test
    fun `test thread inspector throws on main thread`() {
        // Temporarily rename the thread to simulate a main thread execution for the test
        val oldName = Thread.currentThread().name
        Thread.currentThread().name = "main"

        try {
            assertThrows(IllegalStateException::class.java) {
                ThreadInspector.assertNotMainThread()
            }
        } finally {
            Thread.currentThread().name = oldName
        }
    }
}
