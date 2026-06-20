package com.codewiththiru.notifications.config

data class QuietHoursPolicy(
    val enabled: Boolean = false,
    val startHour: Int = 22,
    val startMinute: Int = 0,
    val endHour: Int = 7,
    val endMinute: Int = 0,
    val allowHighPriority: Boolean = true
)
