package com.codewiththiru.platform.growth.onboarding

interface OnboardingManager {
    suspend fun startFlow(flowId: String): OnboardingFlow?
    suspend fun completeStep(flowId: String, stepId: String)
    suspend fun skipFlow(flowId: String)
    suspend fun getFlowProgress(flowId: String): Float
}

class DefaultOnboardingManager : OnboardingManager {
    private val completedSteps = mutableMapOf<String, MutableSet<String>>()
    private val activeFlows = mutableMapOf<String, OnboardingFlow>()

    override suspend fun startFlow(flowId: String): OnboardingFlow? {
        // Load flow config from remote config or locally
        val dummyFlow = OnboardingFlow(flowId, listOf(OnboardingStep("step1"), OnboardingStep("step2")))
        activeFlows[flowId] = dummyFlow
        return dummyFlow
    }

    override suspend fun completeStep(flowId: String, stepId: String) {
        val steps = completedSteps.getOrPut(flowId) { mutableSetOf() }
        steps.add(stepId)
    }

    override suspend fun skipFlow(flowId: String) {
        // Mark the entire flow as dismissed or skipped
        activeFlows.remove(flowId)
    }

    override suspend fun getFlowProgress(flowId: String): Float {
        val flow = activeFlows[flowId] ?: return 0f
        val steps = completedSteps[flowId] ?: return 0f
        if (flow.steps.isEmpty()) return 1f
        return steps.size.toFloat() / flow.steps.size.toFloat()
    }
}
