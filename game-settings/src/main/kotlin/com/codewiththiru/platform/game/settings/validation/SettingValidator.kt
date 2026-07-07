package com.codewiththiru.platform.game.settings.validation

import com.codewiththiru.platform.game.settings.api.SettingValue

/**
 * Functional interface for validating a proposed setting value.
 */
interface SettingValidator {
    /**
     * @return true if the proposed value is valid and within bounds.
     */
    fun isValid(value: SettingValue): Boolean
}

/**
 * Range validator for Integer settings.
 */
class IntRangeValidator(
    private val min: Int,
    private val max: Int,
) : SettingValidator {
    override fun isValid(value: SettingValue): Boolean {
        if (value !is SettingValue.IntValue) return false
        return value.value in min..max
    }
}
