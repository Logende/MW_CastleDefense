package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.components.CDComponentBackground
import org.neubauerfelix.manawars.castledefense.components.CDComponentEntity
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimationSimple
import org.neubauerfelix.manawars.manawars.entities.MEntityImage
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerAction

class UnitInfoBox(x: Float, y: Float, val previewBackground: TextureRegion, val unit: IDataUnit) : MComponentContainer(x, y) {


    val background: IEntity
    val entity: IEntity
    val dummy: IEntity

    init {
        val previewBorder = 30f
        val previewHeight = previewBackground.regionHeight.toFloat()
        val previewWidth = previewBackground.regionWidth.toFloat()
        val previewX = previewBorder
        val previewY = GameConstants.WORLD_HEIGHT_UNITS - previewHeight
        background = MEntityImage(previewX, previewY, previewWidth, previewHeight, previewBackground)
        background.spawn()
        // TODO: draw background in preview area

        MManaWars.m.getCamera().window.centerVertical = GameConstants.WORLD_HEIGHT_UNITS
        entity = unit.produce(background.left + 20f, GameConstants.WORLD_HEIGHT_UNITS - unit.animation.bodyHeight, ControllerAction(),
                MConstants.TEAM_PLAYER)
        entity.speedY = 0f
        entity.accelerationY = 0f
        dummy = unit.produce(background.left + 400f, GameConstants.WORLD_HEIGHT_UNITS - unit.animation.bodyHeight, ControllerAction(),
                MConstants.TEAM_BOT)
        dummy.speedY = 0f
        dummy.accelerationY = 0f


    }




}