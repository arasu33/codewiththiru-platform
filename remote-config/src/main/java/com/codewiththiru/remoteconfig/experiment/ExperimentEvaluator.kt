package com.codewiththiru.remoteconfig.experiment

class ExperimentEvaluator(
    private val bucketingStrategy: UserBucketingStrategy,
    private val stickyAssignmentManager: StickyAssignmentManager,
    private val exposureTracker: ExperimentExposureTracker
) {
    suspend fun evaluate(userId: String, experiment: Experiment): ExperimentVariant? {
        val stickyVariantId = stickyAssignmentManager.getAssignment(experiment.id)
        if (stickyVariantId != null) {
            val variant = experiment.variants.find { it.id == stickyVariantId } 
                ?: if (experiment.controlGroup.id == stickyVariantId) experiment.controlGroup else null
                
            if (variant != null) {
                exposureTracker.trackExposure(userId, experiment.id, variant.id)
                return variant
            }
        }

        val allVariants = mutableListOf<ExperimentVariant>()
        allVariants.add(experiment.controlGroup)
        allVariants.addAll(experiment.variants)

        val variant = bucketingStrategy.assignUserToVariant(userId, experiment.id, allVariants)
        if (variant != null) {
            stickyAssignmentManager.saveAssignment(experiment.id, variant.id)
            exposureTracker.trackExposure(userId, experiment.id, variant.id)
            return variant
        }

        return experiment.controlGroup
    }
}
