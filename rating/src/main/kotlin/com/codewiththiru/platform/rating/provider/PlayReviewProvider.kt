package com.codewiththiru.platform.rating.provider

import android.app.Activity
import com.codewiththiru.platform.rating.model.ReviewLaunchResult

/**
 * Abstraction over Play Core's ReviewManager to allow for testing.
 */
interface PlayReviewProvider {
    /**
     * Attempts to launch the native Play Core In-App Review dialog.
     */
    suspend fun launchReview(activity: Activity): ReviewLaunchResult
}
