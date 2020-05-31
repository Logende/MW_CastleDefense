package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import java.util.*

class BoxEditArmyMenuScrollpaper(x: Float, y: Float, width: Float,
                                 texturePaperTop: TextureRegion, texturePaperBetween: TextureRegion,
                                 editArmyAction: (IDataUnit) -> Unit) :
        MComponentContainer(x, y) {


    val tribeInfoBoxes = mutableListOf<IComponent>()

    init {
        addBackgrounds(texturePaperTop, texturePaperBetween)

        val boxWidth = 1400f
        val boxX = (width - boxWidth) / 2f
        var boxY = 200f


        for (unitType in MWUnitType.values()) {
            if (unitType.main) {

                val box = BoxUnitTypeInfo(boxX, boxY, boxWidth, unitType, 1f, editArmyAction)
                addComponent(box)
                tribeInfoBoxes.add(box)
                boxY += box.height + MConstants.UI_DISTANCE_COLUMNS * 6

            }
        }

    }


    fun addBackgrounds(texturePaperTop: TextureRegion, texturePaperBetween: TextureRegion) {
        var backgroundY = 0f
        val width = texturePaperBetween.regionWidth.toFloat()
        val height = texturePaperBetween.regionHeight.toFloat()
        val backgroundBetweenCount = CDConstants.UI_MENU_MAIN_BACKGROUND_BETWEEN_COUNT
        val menuBackgroundTop = MImage(0f, backgroundY, width, height, texturePaperTop)
        addComponent(menuBackgroundTop)
        backgroundY += GameConstants.SCREEN_HEIGHT

        for (i in 0 until backgroundBetweenCount) {
            val menuBackgroundBetween = MImage(0f, backgroundY, width, height, texturePaperBetween)
            addComponent(menuBackgroundBetween)
            backgroundY += GameConstants.SCREEN_HEIGHT
        }

        val textureFlipped = TextureRegion(texturePaperTop)
        textureFlipped.flip(false, true)

        val menuBackgroundBottom = MImage(0f, backgroundY, width, height, textureFlipped)
        addComponent(menuBackgroundBottom)
    }


}