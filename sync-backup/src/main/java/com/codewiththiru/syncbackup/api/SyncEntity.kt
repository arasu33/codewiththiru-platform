package com.codewiththiru.syncbackup.api

import kotlinx.serialization.Serializable

@Serializable
data class SyncEntity(
    val id: String,
    val collectionName: String,
    val data: String, // Serialized JSON payload
    val metadata: SyncMetadata
)

@Serializable
data class SyncMetadata(
    val createdAtMillis: Long,
    val updatedAtMillis: Long,
    val isDeleted: Boolean = false,
    val dataVersion: DataVersion,
    val deviceId: String
)
