package com.codewiththiru.remoteconfig.featureflags

data class FeatureFlagMetadata(
    val key: String,
    val description: String,
    val owner: FeatureFlagOwner,
    val lifecycle: FeatureFlagLifecycle
)
