package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import org.neubauerfelix.manawars.game.IDisposable
import java.lang.NullPointerException


class MusicHandler : IMusicHandler, IDisposable {

    private var musicVolume: Float = 1f // TODO: music volume option in GUI?
    private var music: Music? = null
    private var currentMusicPath: String? = null


    override fun dispose() {
        if(music != null){
            music!!.stop()
            music!!.dispose()
            music = null
            print("disposing music")
        }
    }

    fun setMusicVolume(f: Float) {
        musicVolume = f
    }

    @Synchronized
    override fun loadMusic(path: String) {
        if (currentMusicPath != null) {
            if (currentMusicPath.equals(path)) {
                return
            }
        }
        dispose()
        val music = Gdx.audio.newMusic(Gdx.files.internal("music/$path.ogg"))
        currentMusicPath = path
        this.music = music
    }

    @Synchronized
    override fun playMusic() {
        val m = music
        m?: throw NullPointerException()

        if (!m.isPlaying) {
            m.isLooping = true
            m.volume = musicVolume
            m.setOnCompletionListener { println("completed song $m") }
            m.play()
        }
    }
}