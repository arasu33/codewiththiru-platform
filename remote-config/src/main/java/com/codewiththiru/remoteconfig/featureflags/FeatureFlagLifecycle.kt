package com.codewiththiru.remoteconfig.featureflags

data class FeatureFlagLifecycle(
    val status: FeatureFlagStatus,
    val createdDate: Long,
    val expiryDate: Long? = null,
    val jiraTicket: String? = null
)
