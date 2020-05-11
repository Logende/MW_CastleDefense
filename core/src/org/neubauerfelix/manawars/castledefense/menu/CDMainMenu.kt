package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration
import org.neubauerfelix.manawars.castledefense.CDScreen
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalFelix
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.castledefense.player.CDControllerHuman
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.menu.MMenuScreen
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CDMainMenu(game: AManaWars) : MMenuScreen(game) {

    val backgroundWood = MBackground("backgrounds/menu_wood.png", 0f, false, game.getAssetLoader())
    var currentTribeIndex = 0
    lateinit var scrollPaper: MainMenuScrollPaperBox

    var currentYGoal = 0f
    var speedY = 0f
    var accelerationY = 0f

    override fun loadMenu() {
        MManaWars.m.getAssetLoader().loadTexture("backgrounds/menu_main_top.png")
        MManaWars.m.getAssetLoader().loadTexture("backgrounds/menu_paper.png")
        backgroundWood.load()
    }

    override fun loadedMenu() {
        //removeComponent(scrollComponent) // TODO: Consider going away from menuscrollable

        backgroundWood.loadedAssets()
        addBackground(backgroundWood)

        //addTitle("ManaWars")

        val m = CDManaWars.cd
        val texturePaperTop = m.getAssetLoader().createTextureRegion("backgrounds/menu_paper.png") //TODO
        val texturePaperBetween = m.getAssetLoader().createTextureRegion("backgrounds/menu_paper.png")
        val paperWidth = texturePaperBetween.regionWidth.toFloat()
        scrollPaper = MainMenuScrollPaperBox(100f, 0f, paperWidth, texturePaperTop, texturePaperBetween) {
            actionFight(it)
        }
        addComponent(scrollPaper)

        val controlsWidth = GameConstants.SCREEN_WIDTH - scrollPaper.right
        val controlsBox = MainMenuControlsBox(scrollPaper.right, 0f, controlsWidth,
                Runnable {
                    actionUp()
                },
                Runnable {
                    actionDown()
                })
        addComponent(controlsBox)

    }


    override fun disposeMenu() {
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_main_top.png")
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
        val tribeHandler = CDManaWars.cd.getTribeHandler()
        val tribePlayer = tribeHandler.getTribe("bear")!! // TODO
        val controllerPlayer = CDControllerBot(CDKITraditionalFelix())
        val controllerEnemy = CDControllerBot(CDKITraditionalFelix())
        // CDControllerBot(CDKIMachineLearning(CDKIModelRandom(), CDKIFeatureExtractor()))
        val config = CDMatchConfiguration(controllerPlayer, controllerEnemy, tribePlayer, tribeEnemy)
        val screen = CDScreen(getGame(), config)
        getGame().startScreen(screen, false)
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