package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.DataUnitDummy
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityImage
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerAction
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerDummy

class BoxUnitInfoComplex(x: Float, y: Float, previewBackground: TextureRegion, val unit: IDataUnit) : MComponentContainer(x, y) {


    val background: IEntity
    val entity: IControlled
    val dummy: IControlled

    init {

        // TODO: Clean this class up and possibly move some constants to CDConstants file
        val boxBorder = 100f
        val boxHeight = previewBackground.regionHeight.toFloat()
        val boxWidth = previewBackground.regionWidth.toFloat()
        val boxY = GameConstants.WORLD_HEIGHT_BACKGROUNDS - boxHeight
        background = MEntityImage(boxBorder, boxY, boxWidth, boxHeight, previewBackground)
        background.spawn()

        val window = MManaWars.m.getCamera().window
        val previousWindowY = window.y
        window.centerVertical = GameConstants.WORLD_HEIGHT_UNITS - 220f
        val offsetY = previousWindowY - window.y

        entity = unit.produce(background.left + 80f,
                GameConstants.WORLD_HEIGHT_UNITS - unit.animation.bodyHeight * unit.animation.defaultScale,
                ControllerAction(),
                MConstants.TEAM_PLAYER)
        entity.speedY = 0f
        entity.accelerationY = 0f


        val dummyUnit = DataUnitDummy()
        dummy = dummyUnit.produce(background.left + 510f,
                GameConstants.WORLD_HEIGHT_UNITS - dummyUnit.animation.bodyHeight * dummyUnit.animation.defaultScale,
                ControllerDummy(),
                MConstants.TEAM_BOT)
        dummy.speedY = 0f
        dummy.accelerationY = 0f
        dummy.goalX = dummy.centerHorizontal

        val tableX = GameConstants.SCREEN_WIDTH - boxBorder - boxWidth
        val infoBoxSimple = BoxUnitInfoSimple(tableX, background.top + offsetY, boxWidth, unit)
        infoBoxSimple.centerVertical = background.centerVertical + offsetY
        addComponent(infoBoxSimple)

    }





}