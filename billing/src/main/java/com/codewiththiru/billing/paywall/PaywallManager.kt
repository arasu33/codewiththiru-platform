package com.codewiththiru.billing.paywall

import android.app.Activity

interface PaywallManager {
    suspend fun showPaywall(activity: Activity, variant: PaywallVariant)
    fun getActiveVariant(): PaywallVariant
}
