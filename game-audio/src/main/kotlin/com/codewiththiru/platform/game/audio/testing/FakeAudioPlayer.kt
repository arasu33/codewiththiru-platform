package com.codewiththiru.platform.game.audio.testing

import com.codewiththiru.platform.game.audio.api.AudioRequest
import com.codewiththiru.platform.game.audio.engine.AudioPlayer

class FakeAudioPlayer : AudioPlayer {
    var currentRequest: AudioRequest? = null
    var currentVolume: Float = 0f
    var playing: Boolean = false

    override suspend fun play(
        request: AudioRequest,
        finalVolume: Float,
    ) {
        currentRequest = request
        currentVolume = finalVolume
        playing = true
    }

    override suspend fun pause() {
        playing = false
    }

    override suspend fun resume() {
        if (currentRequest != null) {
            playing = true
        }
    }

    override suspend fun stop() {
        playing = false
        currentRequest = null
    }

    override suspend fun seekTo(positionMs: Long) {
        // No-op for fake
    }

    override fun setVolume(volume: Float) {
        currentVolume = volume
    }

    override fun isPlaying(): Boolean = playing
}
