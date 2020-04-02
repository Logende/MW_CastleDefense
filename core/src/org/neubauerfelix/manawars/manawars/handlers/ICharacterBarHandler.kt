package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.entities.ILiving

interface ICharacterBarHandler: IHandler {

    fun drawStatsBar(batcher: Batch, e: ILiving)
    fun drawFrame(batcher: Batch, x: Float, y: Float, width: Float, height: Float,
                  color: Color? = null)

}