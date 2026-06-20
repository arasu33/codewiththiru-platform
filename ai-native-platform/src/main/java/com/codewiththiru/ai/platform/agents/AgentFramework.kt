package com.codewiththiru.ai.platform.agents

import com.codewiththiru.ai.platform.api.AIResult

interface Agent {
    val id: String
    val type: AgentType
    suspend fun executeTask(payload: Map<String, Any>): AIResult
}

enum class AgentType {
    TASK, LEARNING, GAMING, GROWTH, MONETIZATION, DEVELOPER, SUPPORT
}

interface AgentRegistry {
    fun registerAgent(agent: Agent)
    fun getAgent(id: String): Agent?
}

interface AgentManager {
    suspend fun dispatchTask(agentId: String, payload: Map<String, Any>): AIResult
}

data class AgentWorkflow(
    val workflowId: String,
    val steps: List<AgentStep>
)

data class AgentStep(val agentId: String, val payloadTemplate: Map<String, Any>)
