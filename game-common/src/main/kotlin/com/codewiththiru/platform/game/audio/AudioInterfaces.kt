package com.codewiththiru.platform.game.audio

@Deprecated("Use AudioManager and AudioConfiguration from :game-audio module instead")
interface VolumeManager {
    var masterVolume: Float
    var musicVolume: Float
    var soundVolume: Float
    var isMuted: Boolean
}

@Deprecated("Use SoundEffectManager from :game-audio module instead")
interface SoundManager : VolumeManager {
    fun preloadSound(soundId: String)

    fun playSound(
        soundId: String,
        loop: Boolean = false,
    )

    fun stopSound(soundId: String)

    fun stopAllSounds()
}

@Deprecated("Use MusicManager from :game-audio module instead")
interface MusicManager : VolumeManager {
    fun playMusic(
        musicId: String,
        loop: Boolean = true,
        fadeMs: Long = 1000,
    )

    fun pauseMusic(fadeMs: Long = 500)

    fun resumeMusic(fadeMs: Long = 500)

    fun stopMusic(fadeMs: Long = 1000)
}
