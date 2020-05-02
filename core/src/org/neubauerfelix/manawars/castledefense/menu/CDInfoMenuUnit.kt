package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.menu.MMenuScreen

class CDInfoMenuUnit(game: AManaWars, val unit: IDataUnit, val tribe: IDataTribe) : MMenuScreen(game, true) {


    val previewBackground: MBackground

    init {
        val allBackgrounds = MManaWars.m.getBackgroundListHandler().backgrounds
        val backgroundTheme = tribe.backgroundThemes.first()
        val chosenBackground = allBackgrounds.first {
            it.startTheme == backgroundTheme && it.endTheme == backgroundTheme
        }
        previewBackground = chosenBackground.produce(0f)
    }

    override fun loadMenu() {
        previewBackground.load()

    }

    override fun loadedMenu() {
        previewBackground.loadedAssets()
        val backgroundTexture = previewBackground.textureRegion!!
        val backgroundTextureSnippet = TextureRegion(backgroundTexture, 0,
                0,
                CDConstants.UI_UNIT_PREVIEW_BOX_WIDTH, -CDConstants.UI_UNIT_PREVIEW_BOX_HEIGHT)

        val g = getGame() as CDManaWars

        val box = UnitInfoBox(0f, 0f, backgroundTextureSnippet, unit)
        addComponent(box)

        addExitButton(g.getLanguageHandler().getMessage("button_back"), 0f, true)


    }


    override fun disposeMenu() {
    }
}