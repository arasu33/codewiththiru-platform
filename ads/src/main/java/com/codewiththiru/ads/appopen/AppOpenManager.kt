package com.codewiththiru.ads.appopen

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.ads.repository.AdsRepository
import com.codewiththiru.ads.state.AdState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Interface representing callbacks for an App Open Ad display.
 */
interface AppOpenCallback {
    fun onAdDismissed()
    fun onAdFailedToShow(error: Throwable)
    fun onAdShowed()
}

/**
 * Enterprise manager specifically designed for App Open Ads.
 * Handles background/foreground lifecycle tracking, ad expiration rules,
 * and splash screen timeouts.
 */
class AppOpenManager(
    private val application: Application,
    private val policy: AppOpenPolicy,
    private val repository: AdsRepository,
    private val analytics: AppOpenAnalytics,
    private val coroutineScope: CoroutineScope
) : DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null
    private var isShowingAd = false
    private var lastAdLoadTimeMs: Long = 0

    init {
        application.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    /**
     * Called when the app moves to the foreground.
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        // Warm start logic
        if (!isShowingAd) {
            analytics.onWarmStartRequest()
            showAdIfAvailable()
        }
    }

    /**
     * Loads the app open ad. Should be called early in the app lifecycle (e.g. splash screen).
     */
    fun loadAd() {
        if (isAdAvailable()) return // Already have a valid ad
        
        coroutineScope.launch {
            repository.load(AdType.AppOpen)
            // Listen for load completion to stamp the load time
            repository.observeState(AdType.AppOpen).first { state ->
                if (state is AdState.Loaded) {
                    lastAdLoadTimeMs = System.currentTimeMillis()
                }
                state is AdState.Loaded || state is AdState.Failed
            }
        }
    }

    /**
     * Attempts to show the ad, waiting up to [timeoutMs] for it to load.
     * Ideal for splash screens so the user isn't stuck forever.
     */
    suspend fun showAdWithTimeout(timeoutMs: Long, callback: AppOpenCallback) {
        analytics.onColdStartRequest()
        
        if (!policy.evaluate(isShowingAd).let { it is AppOpenPolicyResult.Allowed }) {
            callback.onAdDismissed() // Skip ad
            return
        }

        try {
            val success = withTimeoutOrNull(timeoutMs) {
                // Wait until the ad state is Loaded or Failed
                var state = repository.observeState(AdType.AppOpen).value
                while (state !is AdState.Loaded && state !is AdState.Failed) {
                    kotlinx.coroutines.delay(100)
                    state = repository.observeState(AdType.AppOpen).value
                }
                state is AdState.Loaded
            }

            if (success == true && isAdAvailable()) {
                showAd(callback)
            } else {
                analytics.onTimeoutReached()
                callback.onAdDismissed()
                loadAd() // Preload for the next time
            }
        } catch (e: TimeoutCancellationException) {
            analytics.onTimeoutReached()
            callback.onAdDismissed()
        }
    }

    private fun showAdIfAvailable() {
        if (!policy.evaluate(isShowingAd).let { it is AppOpenPolicyResult.Allowed }) return
        if (!isAdAvailable()) {
            loadAd()
            return
        }

        showAd(object : AppOpenCallback {
            override fun onAdDismissed() {
                loadAd() // Preload next
            }
            override fun onAdFailedToShow(error: Throwable) {
                loadAd()
            }
            override fun onAdShowed() {}
        })
    }

    private fun showAd(callback: AppOpenCallback) {
        val activity = currentActivity ?: return
        
        if (repository.observeState(AdType.AppOpen).value is AdState.Loaded) {
            isShowingAd = true
            // In a real implementation, the specific UI provider handles the show result and callbacks.
            // For architecture completeness, we simulate the hook here.
            coroutineScope.launch {
                try {
                    repository.show(AdType.AppOpen)
                    analytics.onImpression("app_open_unit", "AdMob")
                    callback.onAdShowed()
                    
                    // Simulate dismissal after some time or wait for provider orchestrator
                    // In production, the orchestrator triggers this
                    isShowingAd = false
                    callback.onAdDismissed()
                } catch (e: kotlinx.coroutines.CancellationException) {
                    isShowingAd = false
                    throw e
                } catch (e: kotlin.coroutines.cancellation.CancellationException) {
                    isShowingAd = false
                    throw e
                } catch (e: Exception) {
                    isShowingAd = false
                    callback.onAdFailedToShow(e)
                }
            }
        } else {
            callback.onAdFailedToShow(Exception("Ad not ready"))
        }
    }

    private fun isAdAvailable(): Boolean {
        val isLoaded = repository.observeState(AdType.AppOpen).value is AdState.Loaded
        return isLoaded && policy.isAdAvailable(lastAdLoadTimeMs)
    }

    // ActivityLifecycleCallbacks
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
}
