package com.codewiththiru.platform.growth.retention

data class RetentionMetric(
    val dayId: Int, // e.g., 1, 7, 30
    val retainedUsers: Int,
    val totalUsers: Int
) {
    val retentionRate: Double get() = if (totalUsers > 0) retainedUsers.toDouble() / totalUsers else 0.0
}
