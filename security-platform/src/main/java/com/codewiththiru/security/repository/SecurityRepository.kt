package com.codewiththiru.security.repository

import com.codewiththiru.security.api.SecurityState
import kotlinx.coroutines.flow.StateFlow

interface SecurityRepository {
    val state: StateFlow<SecurityState>
    suspend fun verifyIntegrity(): Boolean
}
