package com.codewiththiru.platform.game.settings

import com.codewiththiru.platform.game.settings.api.SettingKey
import com.codewiththiru.platform.game.settings.api.SettingValue
import com.codewiththiru.platform.game.settings.testing.TestSettingMigration
import com.codewiththiru.platform.game.settings.validation.IntRangeValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsTests {
    @Test
    fun `test IntRangeValidator correctly validates bounds`() {
        val validator = IntRangeValidator(0, 100)

        assertTrue(validator.isValid(SettingValue.IntValue(50)))
        assertTrue(validator.isValid(SettingValue.IntValue(0)))
        assertTrue(validator.isValid(SettingValue.IntValue(100)))

        assertFalse(validator.isValid(SettingValue.IntValue(-1)))
        assertFalse(validator.isValid(SettingValue.IntValue(101)))

        // Fails on wrong type
        assertFalse(validator.isValid(SettingValue.BooleanValue(true)))
    }

    @Test
    fun `test migration logic remaps old keys`() {
        val migration = TestSettingMigration()

        val oldKey = SettingKey("music_volume")
        val oldValue = SettingValue.IntValue(50)

        val result = migration.migrate(oldKey, oldValue)

        assertTrue(result != null)
        assertEquals("is_music_enabled", result!!.first.value)
        assertEquals(SettingValue.BooleanValue(true), result.second)
    }
}
