package com.codewiththiru.ads.lifecycle

import com.codewiththiru.ads.api.AdType

/**
 * Manages the lifecycle of ads including load, ready, show, dismiss, expire, 
 * reload, destroy, and recovery operations.
 */
interface AdLifecycleManager {

    fun onLoadRequested(adType: AdType)
    
    fun onReady(adType: AdType)
    
    fun onShowRequested(adType: AdType)
    
    fun onDismissed(adType: AdType)
    
    fun onExpired(adType: AdType)
    
    fun onReloadRequested(adType: AdType)
    
    fun onDestroyRequested(adType: AdType)
    
    fun onRecoveryAttempted(adType: AdType)
}
