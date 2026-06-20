package com.codewiththiru.platform.growth.experiment

data class Experiment(
    val id: String,
    val name: String,
    val variants: List<Variant>,
    val allocations: List<TrafficAllocation>,
    val isActive: Boolean
)
