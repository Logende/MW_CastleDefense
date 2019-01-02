package org.neubauerfelix.manawars.game

import com.badlogic.gdx.Game

open abstract class AManaWars: Game(), IDisposable, IResetable, ILoadableAsync {

    companion object {
        lateinit var m: AManaWars
            private set

        fun init(m: AManaWars){
            this.m = m
        }
    }

    abstract fun getCamera(): ICamera
    abstract fun getEventHandler(): IEventHandler
    abstract fun getImageHandler(): IImageHandler
    abstract fun getAssetLoader(): IAssetLoader

    abstract override fun getScreen(): IScreenTimed
    abstract fun startScreen(screen: IScreenTimed, unloadCurrent: Boolean)
    abstract fun stopScreen()

}