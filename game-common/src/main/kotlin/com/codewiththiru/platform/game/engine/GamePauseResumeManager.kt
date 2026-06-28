package com.codewiththiru.platform.game.engine

interface GamePauseManager {
    fun requestPause()

    fun isPaused(): Boolean
}

interface GameResumeManager {
    fun requestResume()
}
