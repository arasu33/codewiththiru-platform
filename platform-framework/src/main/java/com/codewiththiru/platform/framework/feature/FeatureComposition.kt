package com.codewiththiru.platform.framework.feature

data class FeaturePack(
    val featureId: String,
    val dependencies: List<String> = emptyList(),
    val isOptional: Boolean = false
)

interface FeatureRegistry {
    fun registerFeature(feature: FeaturePack)
    fun getFeature(featureId: String): FeaturePack?
}

interface FeatureResolver {
    fun resolveDependencies(features: List<String>): List<String>
}

interface FeatureComposer {
    fun compose(resolvedFeatures: List<String>)
}
