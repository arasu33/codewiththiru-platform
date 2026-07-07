package com.codewiththiru.platform.core.date

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

/**
 * Thread-safe utility for formatting and parsing dates and times using the modern java.time API.
 *
 * Utilizes a cache of [DateTimeFormatter] instances to optimize formatting performance.
 */
object CustDateFormatter {
    private val formatterCache = ConcurrentHashMap<FormatterKey, DateTimeFormatter>()

    private data class FormatterKey(
        val pattern: String,
        val locale: Locale,
    )

    private fun getOrCreateFormatter(
        pattern: String,
        locale: Locale,
    ): DateTimeFormatter =
        formatterCache.computeIfAbsent(FormatterKey(pattern, locale)) { key ->
            DateTimeFormatter.ofPattern(key.pattern, key.locale)
        }

    /**
     * Formats an [Instant] into a string using the specified pattern, timezone, and locale.
     *
     * @param instant The instant point in time.
     * @param pattern The DateTimeFormatter pattern string.
     * @param zoneId The Target Timezone offset. Defaults to UTC.
     * @param locale The Locale settings to use. Defaults to default locale.
     * @return Formatted string representation.
     */
    fun formatInstant(
        instant: Instant,
        pattern: String,
        zoneId: ZoneId = ZoneOffset.UTC,
        locale: Locale = Locale.getDefault(),
    ): String {
        val formatter = getOrCreateFormatter(pattern, locale).withZone(zoneId)
        return formatter.format(instant)
    }

    /**
     * Formats a [LocalDate] into a string using the specified pattern and locale.
     *
     * @param localDate The local date value.
     * @param pattern The DateTimeFormatter pattern string.
     * @param locale The Locale settings to use. Defaults to default locale.
     * @return Formatted string representation.
     */
    fun formatLocalDate(
        localDate: LocalDate,
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): String {
        val formatter = getOrCreateFormatter(pattern, locale)
        return formatter.format(localDate)
    }

    /**
     * Formats a [LocalDateTime] into a string using the specified pattern and locale.
     *
     * @param localDateTime The local date-time value.
     * @param pattern The DateTimeFormatter pattern string.
     * @param locale The Locale settings to use. Defaults to default locale.
     * @return Formatted string representation.
     */
    fun formatLocalDateTime(
        localDateTime: LocalDateTime,
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): String {
        val formatter = getOrCreateFormatter(pattern, locale)
        return formatter.format(localDateTime)
    }

    /**
     * Formats a [ZonedDateTime] into a string using the specified pattern and locale.
     *
     * @param zonedDateTime The zoned date-time value.
     * @param pattern The DateTimeFormatter pattern string.
     * @param locale The Locale settings to use. Defaults to default locale.
     * @return Formatted string representation.
     */
    fun formatZonedDateTime(
        zonedDateTime: ZonedDateTime,
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): String {
        val formatter = getOrCreateFormatter(pattern, locale)
        return formatter.format(zonedDateTime)
    }

    /**
     * Parses a text sequence into a [ZonedDateTime] using the specified pattern, timezone, and locale.
     *
     * @param text The date time text sequence.
     * @param pattern The format pattern.
     * @param zoneId The default timezone. Defaults to UTC.
     * @param locale The locale settings. Defaults to default locale.
     * @return The parsed [ZonedDateTime].
     */
    fun parseToZonedDateTime(
        text: CharSequence,
        pattern: String,
        zoneId: ZoneId = ZoneOffset.UTC,
        locale: Locale = Locale.getDefault(),
    ): ZonedDateTime {
        val formatter = getOrCreateFormatter(pattern, locale).withZone(zoneId)
        return ZonedDateTime.parse(text, formatter)
    }

    /**
     * Parses a text sequence into an [Instant] using the specified pattern, timezone, and locale.
     *
     * @param text The date time text sequence.
     * @param pattern The format pattern.
     * @param zoneId The default timezone. Defaults to UTC.
     * @param locale The locale settings. Defaults to default locale.
     * @return The parsed [Instant].
     */
    fun parseToInstant(
        text: CharSequence,
        pattern: String,
        zoneId: ZoneId = ZoneOffset.UTC,
        locale: Locale = Locale.getDefault(),
    ): Instant = parseToZonedDateTime(text, pattern, zoneId, locale).toInstant()

    /**
     * Parses a text sequence into a [LocalDate] using the specified pattern and locale.
     *
     * @param text The date text sequence.
     * @param pattern The format pattern.
     * @param locale The locale settings. Defaults to default locale.
     * @return The parsed [LocalDate].
     */
    fun parseToLocalDate(
        text: CharSequence,
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): LocalDate {
        val formatter = getOrCreateFormatter(pattern, locale)
        return LocalDate.parse(text, formatter)
    }

    /**
     * Parses a text sequence into a [LocalDateTime] using the specified pattern and locale.
     *
     * @param text The date time text sequence.
     * @param pattern The format pattern.
     * @param locale The locale settings. Defaults to default locale.
     * @return The parsed [LocalDateTime].
     */
    fun parseToLocalDateTime(
        text: CharSequence,
        pattern: String,
        locale: Locale = Locale.getDefault(),
    ): LocalDateTime {
        val formatter = getOrCreateFormatter(pattern, locale)
        return LocalDateTime.parse(text, formatter)
    }
}
