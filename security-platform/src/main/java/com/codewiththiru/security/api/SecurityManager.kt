package com.codewiththiru.security.api

import kotlinx.coroutines.flow.StateFlow

interface SecurityManager {
    val state: StateFlow<SecurityState>
    
    suspend fun initialize()
    suspend fun performSecurityAudit(): SecurityResult<Unit>
}
