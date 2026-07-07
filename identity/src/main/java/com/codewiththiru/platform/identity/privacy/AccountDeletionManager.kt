package com.codewiththiru.platform.identity.privacy

import com.codewiththiru.platform.identity.api.IdentityResult

interface AccountDeletionManager {
    suspend fun requestAccountDeletion(userId: String): IdentityResult<Unit>

    suspend fun cancelAccountDeletion(userId: String): IdentityResult<Unit>

    suspend fun isDeletionPending(userId: String): Boolean
}
