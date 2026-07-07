package com.codewiththiru.platform.identity.privacy

import com.codewiththiru.platform.identity.api.IdentityResult

interface DataExportManager {
    suspend fun requestDataExport(userId: String): IdentityResult<String> // Returns a job ID or URL

    suspend fun getExportStatus(jobId: String): ExportStatus
}

enum class ExportStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
}
