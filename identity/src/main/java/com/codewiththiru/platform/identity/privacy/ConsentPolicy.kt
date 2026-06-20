package com.codewiththiru.platform.identity.privacy

data class ConsentPolicy(
    val policyId: String,
    val version: String,
    val isMandatory: Boolean
)
