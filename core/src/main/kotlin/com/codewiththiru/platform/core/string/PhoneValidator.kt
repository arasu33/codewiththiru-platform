package com.codewiththiru.platform.core.string

/**
 * Validates phone numbers against standard international formats, including E.164.
 */
object PhoneValidator {
    private val E164_REGEX = Regex("^\\+[1-9]\\d{1,14}$")
    private val IGNORED_CHARS_REGEX = Regex("[\\s\\-\\(\\)]")

    /**
     * Checks if the given [phoneNumber] matches the strict E.164 formatting rule.
     *
     * Format: `+<country-code><subscriber-number>` (up to 15 digits total, first digit of country code cannot be 0).
     *
     * @param phoneNumber The phone number string to validate.
     * @return True if valid E.164 format, false otherwise.
     */
    fun isValidE164(phoneNumber: String?): Boolean {
        if (phoneNumber.isNullOrBlank()) return false
        return E164_REGEX.matches(phoneNumber)
    }

    /**
     * Checks if the phone number matches E.164 standard after stripping common visual spacing
     * characters (spaces, dashes, parentheses).
     *
     * @param phoneNumber The phone number string to validate.
     * @return True if valid international format after stripping common symbols, false otherwise.
     */
    fun isValidInternational(phoneNumber: String?): Boolean {
        if (phoneNumber.isNullOrBlank()) return false
        val normalized = phoneNumber.replace(IGNORED_CHARS_REGEX, "")
        return E164_REGEX.matches(normalized)
    }
}
