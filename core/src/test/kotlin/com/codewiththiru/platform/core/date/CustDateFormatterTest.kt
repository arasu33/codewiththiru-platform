package com.codewiththiru.platform.core.date

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Locale

class CustDateFormatterTest {
    @Test
    fun testFormatAndParseInstant() {
        val instant = Instant.parse("2026-06-19T12:00:00Z")
        val pattern = "yyyy-MM-dd HH:mm:ss"

        val formatted = CustDateFormatter.formatInstant(instant, pattern, ZoneOffset.UTC)
        assertEquals("2026-06-19 12:00:00", formatted)

        val parsed = CustDateFormatter.parseToInstant("2026-06-19 12:00:00", pattern, ZoneOffset.UTC)
        assertEquals(instant, parsed)
    }

    @Test
    fun testFormatLocalDate() {
        val date = LocalDate.of(2026, 6, 19)
        val pattern = "dd/MM/yyyy"

        val formatted = CustDateFormatter.formatLocalDate(date, pattern)
        assertEquals("19/06/2026", formatted)

        val parsed = CustDateFormatter.parseToLocalDate("19/06/2026", pattern)
        assertEquals(date, parsed)
    }

    @Test
    fun testFormatLocalDateTime() {
        val dateTime = LocalDateTime.of(2026, 6, 19, 15, 30, 0)
        val pattern = "yyyy-MM-dd'T'HH:mm:ss"

        val formatted = CustDateFormatter.formatLocalDateTime(dateTime, pattern)
        assertEquals("2026-06-19T15:30:00", formatted)

        val parsed = CustDateFormatter.parseToLocalDateTime("2026-06-19T15:30:00", pattern)
        assertEquals(dateTime, parsed)
    }

    @Test
    fun testFormatZonedDateTime() {
        val zone = ZoneId.of("Asia/Kolkata")
        val zonedDateTime = ZonedDateTime.of(2026, 6, 19, 15, 30, 0, 0, zone)
        val pattern = "yyyy-MM-dd HH:mm:ss Z"

        val formatted = CustDateFormatter.formatZonedDateTime(zonedDateTime, pattern)
        assertEquals("2026-06-19 15:30:00 +0530", formatted)

        val parsed = CustDateFormatter.parseToZonedDateTime("2026-06-19 15:30:00 +0530", pattern, zone)
        assertEquals(zonedDateTime.toInstant(), parsed.toInstant())
    }

    @Test
    fun testTimezoneAwareInstantFormatting() {
        val instant = Instant.parse("2026-06-19T12:00:00Z")
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val zoneKolkata = ZoneId.of("Asia/Kolkata") // UTC +5:30

        val formatted = CustDateFormatter.formatInstant(instant, pattern, zoneKolkata)
        assertEquals("2026-06-19 17:30:00", formatted)
    }

    @Test
    fun testLocaleAwareFormatting() {
        val date = LocalDate.of(2026, 6, 19)
        val pattern = "MMMM" // full month name

        val formattedUS = CustDateFormatter.formatLocalDate(date, pattern, Locale.US)
        assertEquals("June", formattedUS)

        val formattedFrench = CustDateFormatter.formatLocalDate(date, pattern, Locale.FRENCH)
        assertEquals("juin", formattedFrench)
    }
}
