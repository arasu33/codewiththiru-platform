package com.codewiththiru.platform.core.string

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StringValidatorsTest {
    @Test
    fun testEmailValidator() {
        // Valid email addresses
        assertTrue(EmailValidator.isValid("test@example.com"))
        assertTrue(EmailValidator.isValid("user.name+tag@domain.co.uk"))
        assertTrue(EmailValidator.isValid("123@domain.org"))

        // Invalid email addresses
        assertFalse(EmailValidator.isValid(null))
        assertFalse(EmailValidator.isValid(""))
        assertFalse(EmailValidator.isValid("   "))
        assertFalse(EmailValidator.isValid("plainaddress"))
        assertFalse(EmailValidator.isValid("@missingusername.com"))
        assertFalse(EmailValidator.isValid("username@.com"))
        assertFalse(EmailValidator.isValid("username@missingtld"))
    }

    @Test
    fun testPhoneValidatorE164() {
        // Valid E164 numbers
        assertTrue(PhoneValidator.isValidE164("+14155552671"))
        assertTrue(PhoneValidator.isValidE164("+919876543210"))
        assertTrue(PhoneValidator.isValidE164("+442079460958"))

        // Invalid E164 numbers
        assertFalse(PhoneValidator.isValidE164(null))
        assertFalse(PhoneValidator.isValidE164(""))
        assertFalse(PhoneValidator.isValidE164("14155552671")) // missing '+'
        assertFalse(PhoneValidator.isValidE164("+014155552671")) // country code starts with '0'
        assertFalse(PhoneValidator.isValidE164("+1")) // too short (need digits after cc)
        assertFalse(PhoneValidator.isValidE164("+1234567890123456")) // too long (16 digits total)
        assertFalse(PhoneValidator.isValidE164("+1 415 555 2671")) // contains spaces
        assertFalse(PhoneValidator.isValidE164("+1-415-555-2671")) // contains dashes
    }

    @Test
    fun testPhoneValidatorInternational() {
        // Valid international formatted numbers (spaces/symbols cleaned)
        assertTrue(PhoneValidator.isValidInternational("+1 415 555 2671"))
        assertTrue(PhoneValidator.isValidInternational("+1 (415) 555-2671"))
        assertTrue(PhoneValidator.isValidInternational("+91-9876-543-210"))
        assertTrue(PhoneValidator.isValidInternational("+44 20 7946 0958"))

        // Invalid even after normalization
        assertFalse(PhoneValidator.isValidInternational(null))
        assertFalse(PhoneValidator.isValidInternational("14155552671"))
        assertFalse(PhoneValidator.isValidInternational("+1234567890123456"))
    }

    @Test
    fun testUrlEncoder() {
        val original = "hello world & welcome =/?"
        val encoded = UrlEncoder.encode(original)
        assertEquals("hello+world+%26+welcome+%3D%2F%3F", encoded)

        val decoded = UrlEncoder.decode(encoded)
        assertEquals(original, decoded)
    }

    @Test
    fun testHtmlUtilsStripHtml() {
        assertEquals("Hello World", HtmlUtils.stripHtml("<p>Hello <b>World</b></p>"))
        assertEquals("Click Here", HtmlUtils.stripHtml("<a href=\"https://example.com\">Click Here</a>"))
        assertEquals("", HtmlUtils.stripHtml(null))
    }

    @Test
    fun testHtmlUtilsEscapeHtml() {
        val original = "A & B < C > D \" E ' F / G"
        val escaped = HtmlUtils.escapeHtml(original)
        assertEquals("A &amp; B &lt; C &gt; D &quot; E &#x27; F &#x2F; G", escaped)

        assertEquals("", HtmlUtils.escapeHtml(null))
    }

    @Test
    fun testHtmlUtilsUnescapeHtml() {
        val escaped = "A &amp; B &lt; C &gt; D &quot; E &#x27; F &#x2F; G"
        val unescaped = HtmlUtils.unescapeHtml(escaped)
        assertEquals("A & B < C > D \" E ' F / G", unescaped)

        assertEquals("", HtmlUtils.unescapeHtml(null))
    }
}
