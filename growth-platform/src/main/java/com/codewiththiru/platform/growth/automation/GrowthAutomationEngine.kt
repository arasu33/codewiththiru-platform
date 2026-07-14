package com.codewiththiru.platform.growth.automation

interface GrowthAutomationEngine {
    suspend fun evaluateRules(event: String)
    suspend fun registerRule(rule: AutomationRule)
}

class DefaultGrowthAutomationEngine : GrowthAutomationEngine {
    private val rules = java.util.concurrent.CopyOnWriteArrayList<AutomationRule>()

    override suspend fun evaluateRules(event: String) {
        // Evaluate rules
    }

    override suspend fun registerRule(rule: AutomationRule) {
        rules.add(rule)
    }
}
