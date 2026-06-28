package com.codewiththiru.platform.game.audio.session

/**
 * Manages audio focus with the OS (e.g. when a phone call comes in).
 */
interface AudioFocusManager {
    /**
     * Requests audio focus. Returns true if granted.
     */
    suspend fun requestFocus(): Boolean

    /**
     * Abandons audio focus.
     */
    suspend fun abandonFocus()
}

/**
 * Lifecycle observer for audio to pause/resume during app backgrounding.
 */
interface AudioSession {
    /**
     * Invoked when the application goes into the background.
     */
    suspend fun onBackground()

    /**
     * Invoked when the application returns to the foreground.
     */
    suspend fun onForeground()
}
