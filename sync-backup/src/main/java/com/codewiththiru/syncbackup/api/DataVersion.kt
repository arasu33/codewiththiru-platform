package com.codewiththiru.syncbackup.api

import kotlinx.serialization.Serializable

@Serializable
data class DataVersion(
    val versionNumber: Int,
    val schemaVersion: SchemaVersion
)

@Serializable
data class SchemaVersion(
    val major: Int,
    val minor: Int
) : Comparable<SchemaVersion> {
    override fun compareTo(other: SchemaVersion): Int {
        if (major != other.major) return major.compareTo(other.major)
        return minor.compareTo(other.minor)
    }
}
