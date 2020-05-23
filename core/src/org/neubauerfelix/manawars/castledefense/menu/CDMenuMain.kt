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

class CDMenuMain(game: AManaWars) : MMenuScreen(game) {

    val backgroundWood = MBackground("backgrounds/menu_wood.png", 0f, false, game.getAssetLoader())
    var currentTribeIndex = 0
    lateinit var scrollPaper: BoxMainMenuScrollpaper

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

        val fightLamba = { tribe: IDataTribe -> actionFight(tribe) }
        val editArmyLamba = { tribe: IDataTribe -> actionEditArmy(tribe) }
        scrollPaper = BoxMainMenuScrollpaper(100f, 0f, paperWidth, texturePaperTop, texturePaperBetween,
                fightLamba, editArmyLamba)
        addComponent(scrollPaper)

        val controlsWidth = GameConstants.SCREEN_WIDTH - scrollPaper.right
        val controlsBox = BoxMainMenuControls(scrollPaper.right, 0f, controlsWidth,
                Runnable {
                    actionUp()
                },
                Runnable {
                    actionDown()
                })
        addComponent(controlsBox)

    }


    override fun disposeMenu() {
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper_top.png")
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper.png")
        backgroundWood.dispose()
    }



    private fun actionDown() {
        if (currentTribeIndex == scrollPaper.tribeInfoBoxes.size -1) {
            currentTribeIndex = 0
        } else {
            currentTribeIndex ++
        }
        updateCurrentYGoal()
    }

    private fun actionUp() {
        if (currentTribeIndex == 0) {
            currentTribeIndex = scrollPaper.tribeInfoBoxes.size -1
        } else {
            currentTribeIndex --
        }
        updateCurrentYGoal()
    }

    private fun actionFight(tribeEnemy: IDataTribe) {
        val mw = CDManaWars.cd
        val tribePlayer = mw.getProfileHandler().getProfile().tribe
        tribePlayer.castle.enemyCastle = tribeEnemy.castle // TODO: actually already do this when a tribe is selected
        val controllerPlayer= CDControllerType.HUMAN
        val controllerEnemy = CDControllerType.AGGRESSIVE
        val config = CDMatchConfiguration(controllerPlayer, controllerEnemy, tribePlayer, tribeEnemy)
        val screen = CDScreen(mw, config)
        mw.startScreen(screen, false)
    }

    private fun actionEditArmy(tribePlayer: IDataTribe) {
        val mw = CDManaWars.cd
        val screen = CDMenuEditArmy(mw)
        mw.startScreen(screen, false)
    }

    private fun updateCurrentYGoal() {
        val tribeBox = scrollPaper.tribeInfoBoxes[currentTribeIndex]
        currentYGoal = - tribeBox.centerVertical + GameConstants.SCREEN_HEIGHT / 2f
    }


    override fun render(delta: Float) {
        super.render(delta)
        if (this::scrollPaper.isInitialized) {
            updateScrollPaperMovement()
            speedY += accelerationY
            scrollPaper.y += speedY
        }
    }

    fun updateScrollPaperMovement() {
        val walkSpeedMax = 140f
        val accelerationMax = 10f
        val offset = currentYGoal - this.scrollPaper.y
        val distance = abs(offset)
        if (distance > 25f) {
            val slowingDistance = 600f
            val rampedSpeed = walkSpeedMax * (distance / slowingDistance)
            val clippedSpeed = min(rampedSpeed, walkSpeedMax)
            val desiredVelocity = (clippedSpeed / distance) * offset
            accelerationY = MathUtils.truncate((desiredVelocity - this.speedY), accelerationMax)
        } else {
            accelerationY = 0f
            speedY = 0f
        }
    }



}