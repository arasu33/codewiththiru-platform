package com.codewiththiru.ai.safety

data class GovernancePolicy(
    val allowLocalModels: Boolean,
    val enforcePiiScrubbing: Boolean,
    val maxTokensPerUser: Int
)
