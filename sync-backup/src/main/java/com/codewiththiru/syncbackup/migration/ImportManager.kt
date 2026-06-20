package com.codewiththiru.syncbackup.migration

import com.codewiththiru.syncbackup.api.SyncResult
import java.io.File

interface ImportManager {
    suspend fun importData(source: File): SyncResult
}
