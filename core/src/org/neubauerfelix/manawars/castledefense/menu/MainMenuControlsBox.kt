package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class MainMenuControlsBox(x: Float, y: Float, width: Float, actionUp: Runnable, actionDown: Runnable) :
        MComponentContainer(x, y) {



    init {
        val imageHandler = MManaWars.m.getImageHandler()

        val buttonSize = 200f
        val buttonX = (width - buttonSize) / 2f

        val buttonSettings = MButtonSimple(buttonX, 60f, buttonSize, buttonSize,
                imageHandler.getTextureRegionButton("menu.settings"),
                action = Runnable {  })
        addComponent(buttonSettings)

        val textureArrow =  imageHandler.getTextureRegionButton("menu.arrow")
        val textureArrowDown = TextureRegion(textureArrow)
        textureArrowDown.flip(false, true)
        val buttonUp = MButtonSimple(buttonX, 350f, buttonSize, buttonSize, textureArrow,
                action = actionUp)
        addComponent(buttonUp)

        val buttonEditArmy = MButtonSimple(buttonX, buttonUp.bottom + MConstants.UI_DISTANCE_SMALL,
                buttonSize, buttonSize,
                imageHandler.getTextureRegionButton("menu.editarmy"),
                action = Runnable {  })
        addComponent(buttonEditArmy)
        // TODO editArmy Button should show current player castle

        val buttonDown = MButtonSimple(buttonX, buttonEditArmy.bottom + MConstants.UI_DISTANCE_SMALL,
                buttonSize, buttonSize, textureArrowDown,
                action = actionDown)
        addComponent(buttonDown)


    }




}