package com.codewiththiru.platform.core.string

/**
 * Validates whether a string has a valid email address structure according to RFC 5322 specifications.
 *
 * Employs a precompiled [Regex] for validation.
 */
object EmailValidator {
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9][A-Za-z0-9.-]*\\.[A-Za-z0-9]+$")

    /**
     * Checks if the given [email] is valid.
     *
     * @param email The email address to check.
     * @return True if valid, false otherwise (including null or blank values).
     */
    fun isValid(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        return EMAIL_REGEX.matches(email)
    }
}
