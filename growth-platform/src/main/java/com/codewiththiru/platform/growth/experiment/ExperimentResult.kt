package com.codewiththiru.platform.growth.experiment

data class ExperimentResult(
    val experimentId: String,
    val assignedVariantId: String,
    val isSticky: Boolean
)
