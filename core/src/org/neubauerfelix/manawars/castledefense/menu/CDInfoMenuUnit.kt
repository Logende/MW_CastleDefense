package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MImage
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.menu.MMenuScreen

class CDInfoMenuUnit(game: AManaWars, val unit: IDataUnit, val tribe: IDataTribe) : MMenuScreen(game, true) {


    val previewBackground: MBackground
    lateinit var unitBox: UnitInfoBoxComplex

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
        MManaWars.m.getAssetLoader().loadTexture(CDConstants.UI_MENU_UNITINFO_BACKGROUND)
    }

    override fun loadedMenu() {
        previewBackground.loadedAssets()
        val backgroundTexture = previewBackground.textureRegion!!
        val backgroundTextureSnippet = TextureRegion(backgroundTexture, 0,
                0,
                CDConstants.UI_MENU_UNITINFO_UNIT_PREVIEW_BOX_WIDTH, -CDConstants.UI_MENU_UNITINFO_UNIT_PREVIEW_BOX_HEIGHT)

        val g = getGame() as CDManaWars

        val menuBackground = MImage(0f, 0f, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT,
                MManaWars.m.getAssetLoader().createTextureRegion(CDConstants.UI_MENU_UNITINFO_BACKGROUND))
        addComponent(menuBackground)

        unitBox = UnitInfoBoxComplex(0f, 0f, backgroundTextureSnippet, unit)
        addComponent(unitBox)


        addExitButton(g.getLanguageHandler().getMessage("button_back"), 0f, true)


    }

    override fun render(delta: Float) {
        super.render(delta)
        if (this::unitBox.isInitialized) {
            // TODO: add background with frame that is cut out at preview area and drawn above skills
            for (entity in getEntities { it is MSkill }) {
                val s = entity as MSkill
                s.invisible = !unitBox.background.isInside(s.x, s.y)
            }
        }
        MManaWars.m.getCollisionHandler().updateCollisions(MManaWars.m.screen.getEntities { true })
    }

    override fun disposeMenu() {
    }
}