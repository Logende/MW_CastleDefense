package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.entities.IEntity


abstract class GameScreenScreenTimed(game: AManaWars, drawBackgroundsStatic: Boolean, drawComponentsStatic: Boolean):
        GameScreen(game, drawBackgroundsStatic, drawComponentsStatic), IScreenTimed {


    private var timeDurationStored: Long = 0



    override fun loaded() {
        super.loaded()
        timeDurationStored = 0
    }

    override fun logic(delta: Float, entities: List<IEntity>) {
        this.timeDurationStored+= (delta * 1000).toLong()
    }


    override fun getGameTime(): Long {
        return timeDurationStored
    }



}