package com.codewiththiru.syncbackup.migration

import com.codewiththiru.syncbackup.api.SyncResult
import java.io.File

interface ExportManager {
    suspend fun exportData(format: ExportFormat, destination: File): SyncResult
    suspend fun exportDataSelective(format: ExportFormat, destination: File, collections: List<String>): SyncResult
}
