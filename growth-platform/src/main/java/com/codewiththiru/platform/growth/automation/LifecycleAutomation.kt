package com.codewiththiru.platform.growth.automation

class LifecycleAutomation(private val automationEngine: GrowthAutomationEngine) {
    suspend fun startLifecycleTracking() {
        // Set up standard rules
        automationEngine.registerRule(AutomationRule("reactivation", "inactivity_7d", "true", "send_push"))
    }
}
