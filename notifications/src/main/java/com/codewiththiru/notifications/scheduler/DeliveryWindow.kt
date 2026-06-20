package com.codewiththiru.notifications.scheduler

data class DeliveryWindow(
    val startHour: Int,
    val endHour: Int,
    val isLocalTime: Boolean = true
) {
    fun isValid(currentHour: Int): Boolean {
        return if (startHour <= endHour) {
            currentHour in startHour..endHour
        } else {
            currentHour >= startHour || currentHour <= endHour
        }
    }
}
