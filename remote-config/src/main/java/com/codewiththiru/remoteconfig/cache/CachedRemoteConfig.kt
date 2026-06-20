package com.codewiththiru.remoteconfig.cache

import kotlinx.serialization.Serializable

@Serializable
data class CachedRemoteConfig(
    val schemaVersion: Int,
    val lastUpdated: Long,
    val payload: String
)
