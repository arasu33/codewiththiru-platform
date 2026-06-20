package com.codewiththiru.syncbackup.backup

import kotlinx.serialization.Serializable

@Serializable
data class BackupManifest(
    val snapshotId: String,
    val files: List<String>,
    val metadata: BackupMetadata
)

@Serializable
data class BackupMetadata(
    val timestampMillis: Long,
    val deviceId: String,
    val osVersion: String,
    val appVersion: String,
    val schemaVersionMajor: Int,
    val schemaVersionMinor: Int,
    val isEncrypted: Boolean
)

@Serializable
data class BackupSnapshot(
    val manifest: BackupManifest,
    val dataPayloads: Map<String, String>
)
