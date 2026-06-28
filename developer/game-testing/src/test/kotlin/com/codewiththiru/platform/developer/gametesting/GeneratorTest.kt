package com.codewiththiru.platform.developer.gametesting

import com.codewiththiru.platform.developer.gametesting.generator.RandomProfileGenerator
import org.junit.Assert.assertNotNull
import org.junit.Test

class GeneratorTest {
    @Test
    fun `test random profile generator produces valid objects`() {
        val profile = RandomProfileGenerator.generate()
        assertNotNull(profile.id)
        assertNotNull(profile.level.currentLevel)
    }
}
