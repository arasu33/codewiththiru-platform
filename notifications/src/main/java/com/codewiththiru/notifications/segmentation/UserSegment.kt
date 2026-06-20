package com.codewiththiru.notifications.segmentation

data class UserSegment(
    val id: String,
    val name: String,
    val description: String,
    val rules: List<SegmentRule>
)

sealed class SegmentRule {
    data class DaysSinceRegistration(val minDays: Int, val maxDays: Int?) : SegmentRule()
    data class LastActiveDaysAgo(val minDays: Int, val maxDays: Int?) : SegmentRule()
    data class HasPremium(val isPremium: Boolean) : SegmentRule()
}
