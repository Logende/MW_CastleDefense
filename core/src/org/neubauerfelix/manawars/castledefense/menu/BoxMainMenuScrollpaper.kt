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

class BoxMainMenuScrollpaper(x: Float, y: Float, width: Float,
                             texturePaperTop: TextureRegion, texturePaperBetween: TextureRegion,
                             fightAction: (IDataTribe) -> Unit,
                             editArmyAction: (IDataTribe) -> Unit) :
        MComponentContainer(x, y) {


    val tribeInfoBoxes = mutableListOf<IComponent>()

    init {
        val lang = MManaWars.m.getLanguageHandler()
        addBackgrounds(texturePaperTop, texturePaperBetween)


        val boxWidth = 1150f
        val boxX = (width - boxWidth) / 2f
        var boxY = 200f

        val playerTribe = CDManaWars.cd.getProfileHandler().getProfile().tribe
        val boxEdit = BoxTribeInfo(boxX, boxY, boxWidth, playerTribe, CDConstants.UI_MENU_MAIN_UNIT_SCALE,
                lang.getMessage("menu_edit_army"), editArmyAction)
        addComponent(boxEdit)
        tribeInfoBoxes.add(boxEdit)
        boxY += boxEdit.height + MConstants.UI_DISTANCE_COLUMNS * 14


        for (tribe in CDManaWars.cd.getTribeHandler().listTribes()) {
            val box = BoxTribeInfo(boxX, boxY, boxWidth, tribe, CDConstants.UI_MENU_MAIN_UNIT_SCALE,
                    lang.getMessage("menu_fight"), fightAction)
            addComponent(box)
            tribeInfoBoxes.add(box)
            boxY += box.height + MConstants.UI_DISTANCE_COLUMNS * 14
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