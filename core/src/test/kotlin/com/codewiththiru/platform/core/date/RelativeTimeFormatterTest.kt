package com.codewiththiru.platform.core.date

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.ZoneOffset

class RelativeTimeFormatterTest {
    private val formatter = RelativeTimeFormatter()

    @Test
    fun testJustNow() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = to.minusSeconds(30)
        assertEquals("Just now", formatter.formatRelative(from, to))
    }

    @Test
    fun testMinutesAgo() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = to.minusSeconds(180) // 3 minutes
        assertEquals("3 minutes ago", formatter.formatRelative(from, to))
    }

    @Test
    fun testHoursAgo() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = to.minusSeconds(7200) // 2 hours
        assertEquals("2 hours ago", formatter.formatRelative(from, to))
    }

    @Test
    fun testYesterday() {
        val to = Instant.parse("2026-06-19T12:00:00Z") // June 19
        val from = Instant.parse("2026-06-18T18:00:00Z") // June 18
        assertEquals("Yesterday", formatter.formatRelative(from, to, ZoneOffset.UTC))
    }

    @Test
    fun testDaysAgo() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = Instant.parse("2026-06-15T12:00:00Z") // 4 days ago
        assertEquals("4 days ago", formatter.formatRelative(from, to, ZoneOffset.UTC))
    }

    @Test
    fun testWeeksAgo() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = Instant.parse("2026-06-05T12:00:00Z") // 14 days ago (2 weeks)
        assertEquals("2 weeks ago", formatter.formatRelative(from, to, ZoneOffset.UTC))
    }

    @Test
    fun testMonthsAgo() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = Instant.parse("2026-04-19T12:00:00Z") // 2 months ago (approx 61 days)
        assertEquals("2 months ago", formatter.formatRelative(from, to, ZoneOffset.UTC))
    }

    @Test
    fun testYearsAgo() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = Instant.parse("2023-06-19T12:00:00Z") // 3 years ago
        assertEquals("3 years ago", formatter.formatRelative(from, to, ZoneOffset.UTC))
    }

    @Test
    fun testFutureOrNegativeDuration() {
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = to.plusSeconds(30)
        assertEquals("Just now", formatter.formatRelative(from, to))
    }

    @Test
    fun testCustomRelativeTimeStrings() {
        val spanishStrings =
            object : RelativeTimeStrings {
                override fun justNow(): String = "Ahora mismo"

                override fun minutesAgo(count: Long): String = "Hace $count minutos"

                override fun hoursAgo(count: Long): String = "Hace $count horas"

                override fun yesterday(): String = "Ayer"

                override fun daysAgo(count: Long): String = "Hace $count días"

                override fun weeksAgo(count: Long): String = "Hace $count semanas"

                override fun monthsAgo(count: Long): String = "Hace $count meses"

                override fun yearsAgo(count: Long): String = "Hace $count años"
            }

        val spanishFormatter = RelativeTimeFormatter(spanishStrings)
        val to = Instant.parse("2026-06-19T12:00:00Z")
        val from = to.minusSeconds(600) // 10 minutes

        assertEquals("Hace 10 minutos", spanishFormatter.formatRelative(from, to))
    }
}
