package com.codewiththiru.syncbackup.restore

import com.codewiththiru.syncbackup.backup.BackupManifest
import com.codewiththiru.syncbackup.api.SchemaVersion

class RestoreValidator {
    fun validateManifest(manifest: BackupManifest, currentSchema: SchemaVersion, policy: RestorePolicy): Boolean {
        val manifestSchema = SchemaVersion(manifest.metadata.schemaVersionMajor, manifest.metadata.schemaVersionMinor)
        if (manifestSchema > currentSchema && !policy.allowDowngrade) {
            return false // Cannot restore newer backup onto older app unless allowed
        }
        return true
    }
}
