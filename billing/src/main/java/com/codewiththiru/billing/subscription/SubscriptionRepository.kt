package com.codewiththiru.billing.subscription

interface SubscriptionRepository {
    suspend fun getActiveSubscriptions(): List<SubscriptionStatus>
    suspend fun refreshSubscriptions()
}
