package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.IAnimatedLiving
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWArmorType
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface ICharacterBarHandler: IHandler {

    fun drawStatsBar(batcher: Batch, e: IControlled)
    fun drawArmorFrame(batcher: Batch, x: Float, y: Float, width: Float, height: Float,
                       animationType: MWEntityAnimationType, armor: Map<MWArmorHolder, MWArmorType>)

}