package com.codewiththiru.platform.updates.internal

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultVersionComparatorTest {

    private val comparator = DefaultVersionComparator()

    @Test
    fun `compare returns 0 for equal versions`() {
        assertEquals(0, comparator.compare("1.0.0", "1.0.0"))
        assertEquals(0, comparator.compare("1.2", "1.2.0"))
        assertEquals(0, comparator.compare("2", "2.0.0.0"))
    }

    @Test
    fun `compare returns positive when first is greater`() {
        assertTrue(comparator.compare("1.0.1", "1.0.0") > 0)
        assertTrue(comparator.compare("2.0.0", "1.9.9") > 0)
        assertTrue(comparator.compare("1.2", "1.1.9") > 0)
    }

    @Test
    fun `compare returns negative when second is greater`() {
        assertTrue(comparator.compare("1.0.0", "1.0.1") < 0)
        assertTrue(comparator.compare("1.9.9", "2.0.0") < 0)
        assertTrue(comparator.compare("1.1.9", "1.2") < 0)
    }
}
