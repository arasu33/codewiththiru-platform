package com.codewiththiru.remoteconfig.experiment

data class ExperimentVariant(
    val id: String,
    val weight: Int, // Percentage weight for rollout
    val payload: Map<String, Any> = emptyMap()
)

data class Experiment(
    val id: String,
    val controlGroup: ExperimentVariant,
    val variants: List<ExperimentVariant>,
    val targetSegments: List<String> = emptyList()
)

interface ExperimentManager {
    fun getAssignedVariant(experimentId: String, userId: String): ExperimentVariant?
    fun syncExperiments(experiments: List<Experiment>)
}

class DefaultExperimentManager : ExperimentManager {
    private val experimentsMap = java.util.concurrent.ConcurrentHashMap<String, Experiment>()

    override fun getAssignedVariant(experimentId: String, userId: String): ExperimentVariant? {
        val experiment = experimentsMap[experimentId] ?: return null
        
        // Sticky assignment using consistent hashing
        val hash = "$experimentId-$userId".hashCode() % 100
        val normalizedHash = if (hash < 0) hash + 100 else hash

        var cumulativeWeight = experiment.controlGroup.weight
        if (normalizedHash < cumulativeWeight) {
            return experiment.controlGroup
        }

        for (variant in experiment.variants) {
            cumulativeWeight += variant.weight
            if (normalizedHash < cumulativeWeight) {
                return variant
            }
        }

        return experiment.controlGroup
    }

    override fun syncExperiments(experiments: List<Experiment>) {
        experimentsMap.clear()
        experiments.forEach { experimentsMap[it.id] = it }
    }
}
