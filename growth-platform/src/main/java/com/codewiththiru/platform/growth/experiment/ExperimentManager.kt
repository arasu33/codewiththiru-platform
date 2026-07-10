package com.codewiththiru.platform.growth.experiment

interface ExperimentManager {
    suspend fun fetchExperiment(experimentId: String): Experiment?
    suspend fun getAssignedVariant(experimentId: String, userId: String): ExperimentResult?
    suspend fun trackExposure(experimentId: String, userId: String)
}

class DefaultExperimentManager : ExperimentManager {
    private val experiments = java.util.concurrent.ConcurrentHashMap<String, Experiment>()
    private val assignments = java.util.concurrent.ConcurrentHashMap<String, String>()

    override suspend fun fetchExperiment(experimentId: String): Experiment? {
        return experiments[experimentId]
    }

    override suspend fun getAssignedVariant(experimentId: String, userId: String): ExperimentResult? {
        val exp = experiments[experimentId] ?: return null
        if (!exp.isActive) return null

        val assignmentKey = "${experimentId}_${userId}"
        val existing = assignments[assignmentKey]
        if (existing != null) {
            return ExperimentResult(experimentId, existing, true)
        }

        // Pseudo-random assignment based on user hash
        val hash = userId.hashCode() % 100
        var currentAcc = 0
        var assignedId = exp.variants.firstOrNull()?.id ?: return null

        for (allocation in exp.allocations) {
            currentAcc += allocation.percentage
            if (hash < currentAcc) {
                assignedId = allocation.variantId
                break
            }
        }

        assignments[assignmentKey] = assignedId
        return ExperimentResult(experimentId, assignedId, false)
    }

    override suspend fun trackExposure(experimentId: String, userId: String) {
        // Log exposure to Analytics
    }
}
