package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class SoundHandler : ISoundHandler, IDisposable, ILoadableContent {

    val sounds = HashMap<String, Sound>()
    private var soundVolume: Float = 0.3f // TODO: sound volume option?
    override var loadedContent: Boolean = false


    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true
            val handlerConfigNames = gameConfig.getStringList("sounds")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
                for (soundName in handlerConfig.getStringList("sounds")) {
                    loadSound(soundName)
                }
            }
        }
    }

    override fun dispose() {
        for (sound in sounds.values) {
            sound.dispose()
        }
        sounds.clear()
    }

    fun setSoundVolume(f: Float) {
        soundVolume = f
    }

    /**
     * Loads an existing sound. Requires the sound to not be loaded yet.
     * @param path Name of the MP3 sound to load (excluding the `.mp3` suffix). The sound file must exist within the sounds folder.
     */
    fun loadSound(path: String) {
        if (sounds.containsKey(path)) {
            throw RuntimeException("Unable to load sound: $path: Already loaded.")
        }
        sounds[path] = Gdx.audio.newSound(Gdx.files.internal("sounds/$path.mp3"))
    }


    /**
     * Play a sound using it's path name from the given x coordinate.
     * @param name Sound path.
     * @param x X coordinate of the sound source. The sound volume will be adapted to the player distance from the sound
     * and the direction will be adapted to the real direction of the source within the game relative to the player.
     */
    override fun playSound(name: String, x: Float) {
        if (sounds.containsKey(name)) {
            val sound = sounds[name]
            val cameraCenterHor = MManaWars.m.getCamera().window.centerHorizontal
            val distance: Float = x - cameraCenterHor
            val distanceFactor = 500f / distance
            val volume = min(1.0f, abs(distanceFactor)) * soundVolume
            val pan = min(1f, max(-1f, distanceFactor)) // todo: not sure if PAN is correct
            sound!!.play(volume, 1f, pan)
        } else {
            throw NullPointerException("Unable to play sound '$name': it is not loaded.")
        }
    }
}