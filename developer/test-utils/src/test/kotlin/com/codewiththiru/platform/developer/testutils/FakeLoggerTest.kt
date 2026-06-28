package com.codewiththiru.platform.developer.testutils

import org.junit.Assert.assertEquals
import org.junit.Test

class FakeLoggerTest {
    @Test
    fun `test logging and clearing`() {
        val logger = FakeLogger()
        logger.log("Hello")
        logger.log("World")

        assertEquals(2, logger.messages.size)
        assertEquals("Hello", logger.messages[0])

        logger.clear()
        assertEquals(0, logger.messages.size)
    }
}
