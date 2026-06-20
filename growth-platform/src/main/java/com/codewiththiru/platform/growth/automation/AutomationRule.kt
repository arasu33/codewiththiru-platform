package com.codewiththiru.platform.growth.automation

data class AutomationRule(
    val id: String,
    val triggerEvent: String,
    val condition: String,
    val action: String
)
