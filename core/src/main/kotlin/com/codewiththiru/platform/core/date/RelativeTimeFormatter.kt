package com.codewiththiru.platform.core.date

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

/**
 * Interface representing string resources used for relative time formatting.
 *
 * Can be implemented to provide localized versions.
 */
interface RelativeTimeStrings {
    /** @return string for current moment */
    fun justNow(): String

    /** @return string for relative minutes */
    fun minutesAgo(count: Long): String

    /** @return string for relative hours */
    fun hoursAgo(count: Long): String

    /** @return string for yesterday */
    fun yesterday(): String

    /** @return string for relative days */
    fun daysAgo(count: Long): String

    /** @return string for relative weeks */
    fun weeksAgo(count: Long): String

    /** @return string for relative months */
    fun monthsAgo(count: Long): String

    /** @return string for relative years */
    fun yearsAgo(count: Long): String
}

/**
 * Default English implementation of [RelativeTimeStrings].
 */
class DefaultRelativeTimeStrings : RelativeTimeStrings {
    override fun justNow(): String = "Just now"

    override fun minutesAgo(count: Long): String = if (count == 1L) "1 minute ago" else "$count minutes ago"

    override fun hoursAgo(count: Long): String = if (count == 1L) "1 hour ago" else "$count hours ago"

    override fun yesterday(): String = "Yesterday"

    override fun daysAgo(count: Long): String = "$count days ago"

    override fun weeksAgo(count: Long): String = if (count == 1L) "1 week ago" else "$count weeks ago"

    override fun monthsAgo(count: Long): String = if (count == 1L) "1 month ago" else "$count months ago"

    override fun yearsAgo(count: Long): String = if (count == 1L) "1 year ago" else "$count years ago"
}

/**
 * Utility for formatting instances in time to relative strings (e.g. "Just now", "Yesterday").
 *
 * Thread-safe and supporting custom locale/language strings.
 */
class RelativeTimeFormatter(
    private val strings: RelativeTimeStrings = DefaultRelativeTimeStrings(),
) {
    /**
     * Formats the relative time between [from] and [to] based on the calendar date boundaries
     * defined in the target [zoneId].
     *
     * @param from The past/start instant.
     * @param to The future/end instant (typically "now").
     * @param zoneId The Timezone context used to compute calendar days (e.g., to resolve "Yesterday").
     * @return A localized relative time string.
     */
    fun formatRelative(
        from: Instant,
        to: Instant,
        zoneId: ZoneId = ZoneOffset.UTC,
    ): String {
        val duration = Duration.between(from, to)
        val seconds = duration.seconds

        if (seconds < 0) {
            return strings.justNow()
        }

        val fromLocalDate = from.atZone(zoneId).toLocalDate()
        val toLocalDate = to.atZone(zoneId).toLocalDate()
        val daysBetween = ChronoUnit.DAYS.between(fromLocalDate, toLocalDate)

        return when {
            daysBetween == 0L -> {
                val minutes = seconds / SECONDS_IN_MINUTE
                val hours = minutes / MINUTES_IN_HOUR
                when {
                    seconds < SECONDS_IN_MINUTE -> strings.justNow()
                    minutes < MINUTES_IN_HOUR -> strings.minutesAgo(minutes)
                    else -> strings.hoursAgo(hours)
                }
            }
            daysBetween == 1L -> strings.yesterday()
            daysBetween < DAYS_IN_WEEK -> strings.daysAgo(daysBetween)
            daysBetween < DAYS_IN_MONTH -> {
                val weeks = daysBetween / DAYS_IN_WEEK
                strings.weeksAgo(weeks)
            }
            daysBetween < DAYS_IN_YEAR -> {
                val months = daysBetween / DAYS_IN_MONTH
                strings.monthsAgo(months)
            }
            else -> {
                val years = daysBetween / DAYS_IN_YEAR
                strings.yearsAgo(years)
            }
        }
    }

    companion object {
        private const val SECONDS_IN_MINUTE = 60L
        private const val MINUTES_IN_HOUR = 60L
        private const val DAYS_IN_WEEK = 7L
        private const val DAYS_IN_MONTH = 30L
        private const val DAYS_IN_YEAR = 365L
    }
}
