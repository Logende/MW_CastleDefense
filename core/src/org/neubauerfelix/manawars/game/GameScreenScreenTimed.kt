package org.neubauerfelix.manawars.game


abstract class GameScreenScreenTimed(game: AManaWars, drawBackgroundsStatic: Boolean): GameScreen(game, drawBackgroundsStatic), IScreenTimed {


    private var timeStart: Long = 0
    private var timeDurationStored: Long = 0



    override fun loaded() {
        super.loaded()
        timeStart = System.currentTimeMillis()
        timeDurationStored = 0
    }

    override fun pause() {
        timeDurationStored = getGameTime()
        timeStart = -1
        super.pause()
    }


    override fun resume() {
        super.resume()
        timeStart = System.currentTimeMillis()
    }


    override fun getGameTime(): Long {
        return if(getState() == ScreenState.RUNNING) {
            ((System.currentTimeMillis() - timeStart) * getTimeSpeedModifier() + timeDurationStored).toLong()
        }else {
            timeDurationStored
        }
    }



}