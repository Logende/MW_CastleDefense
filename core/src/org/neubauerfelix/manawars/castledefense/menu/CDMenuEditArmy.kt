package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.menu.MMenuScreenScrollable

class CDMenuEditArmy(game: AManaWars) : MMenuScreenScrollable(game) {

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

        val editArmyAction = { unit: IDataUnit -> editArmyAction(unit) }
        scrollPaper = BoxEditArmyMenuScrollpaper(100f, 0f, paperWidth, texturePaperTop, texturePaperBetween,
                editArmyAction)
        addComponent(scrollPaper)


        addActionButton("Back",  Runnable {
            val screen = CDMenuMain(game)
            game.startScreen(screen, true)
        }, 0f, true)

    }

    fun editArmyAction(unit: IDataUnit) {
        // TODO: Open unit comparison screen where unit can be changed or kept
        val mw = CDManaWars.cd
        val tribe = IDataTribe.findTribe(mw.getTribeHandler().listTribes(), unit)
        val prof = mw.getProfileHandler().getProfile()
        prof.tribe.replaceUnit(unit.unitType, tribe)
    }


    override fun disposeMenu() {
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper_top.png")
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper.png")
        backgroundWood.dispose()
    }

    override fun movedCameraVertical(yDiff: Float) {
    }
}