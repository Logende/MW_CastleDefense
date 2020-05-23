package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDControllerType
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration
import org.neubauerfelix.manawars.castledefense.CDScreen
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.menu.MMenuScreen
import kotlin.math.abs
import kotlin.math.min

class CDMenuEditArmy(game: AManaWars) : MMenuScreen(game) {

    val backgroundWood = MBackground("backgrounds/menu_wood.png", 0f, false, game.getAssetLoader())
    var currentTribeIndex = 0
    lateinit var scrollPaper: BoxEditArmyMenuScrollpaper

    var currentYGoal = 0f
    var speedY = 0f
    var accelerationY = 0f

    override fun loadMenu() {
        MManaWars.m.getAssetLoader().loadTexture("backgrounds/menu_paper_top.png")
        MManaWars.m.getAssetLoader().loadTexture("backgrounds/menu_paper.png")
        backgroundWood.load()
    }

    override fun loadedMenu() {
        //removeComponent(scrollComponent) // TODO: Consider going away from menuscrollable

        backgroundWood.loadedAssets()
        addBackground(backgroundWood)

        //addTitle("ManaWars")

        val m = CDManaWars.cd
        val texturePaperTop = m.getAssetLoader().createTextureRegion("backgrounds/menu_paper_top.png") //TODO
        val texturePaperBetween = m.getAssetLoader().createTextureRegion("backgrounds/menu_paper.png")
        val paperWidth = texturePaperBetween.regionWidth.toFloat()

        scrollPaper = BoxEditArmyMenuScrollpaper(100f, 0f, paperWidth, texturePaperTop, texturePaperBetween)
        addComponent(scrollPaper)

    }


    override fun disposeMenu() {
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper_top.png")
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper.png")
        backgroundWood.dispose()
    }

    private fun updateCurrentYGoal() {
        val tribeBox = scrollPaper.tribeInfoBoxes[currentTribeIndex]
        currentYGoal = - tribeBox.centerVertical + GameConstants.SCREEN_HEIGHT / 2f
    }



}