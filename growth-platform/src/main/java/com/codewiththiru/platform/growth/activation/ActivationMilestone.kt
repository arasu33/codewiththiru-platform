package com.codewiththiru.platform.growth.activation

data class ActivationMilestone(
    val milestoneId: String,
    val requiredScore: Int,
    val isAchieved: Boolean = false
)
