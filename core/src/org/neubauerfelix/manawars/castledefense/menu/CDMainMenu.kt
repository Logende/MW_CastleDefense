package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
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
        scrollPaper = MainMenuScrollPaperBox(100f, 0f, paperWidth, texturePaperTop, texturePaperBetween)
        addComponent(scrollPaper)

        val controlsWidth = GameConstants.SCREEN_WIDTH - scrollPaper.right
        val controlsBox = MainMenuControlsBox(scrollPaper.right, 0f, controlsWidth,
                Runnable {
                    if (currentTribeIndex == 0) {
                        currentTribeIndex = scrollPaper.tribeInfoBoxes.size -1
                    } else {
                        currentTribeIndex --
                    }
                    val tribeBox = scrollPaper.tribeInfoBoxes[currentTribeIndex]
                    currentYGoal = - tribeBox.centerVertical + GameConstants.SCREEN_HEIGHT / 2f
                },
                Runnable {
                    if (currentTribeIndex == scrollPaper.tribeInfoBoxes.size -1) {
                        currentTribeIndex = 0
                    } else {
                        currentTribeIndex ++
                    }
                    val tribeBox = scrollPaper.tribeInfoBoxes[currentTribeIndex]
                    currentYGoal = - tribeBox.centerVertical + GameConstants.SCREEN_HEIGHT / 2f
                })
        addComponent(controlsBox)

    }

    override fun disposeMenu() {
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_main_top.png")
        MManaWars.m.getAssetLoader().unloadAsset("backgrounds/menu_paper.png")
        backgroundWood.dispose()
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
        val walkSpeedMax = 100f
        val accelerationMax = 10f
        val offset = currentYGoal - this.scrollPaper.y
        val distance = abs(offset)
        if (distance > 18f) {
            val slowingDistance = 300f
            val rampedSpeed = walkSpeedMax * (distance / slowingDistance)
            val clippedSpeed = Math.min(rampedSpeed, walkSpeedMax)
            val desiredVelocity = (clippedSpeed / distance) * offset
            accelerationY = truncate((desiredVelocity - this.speedY), accelerationMax)
        } else {
            accelerationY = 0f
            speedY = 0f
        }
    }


    private fun truncate(v : Float, maxLength: Float): Float {
        return if (v > 0) {
            min(v, maxLength)
        } else {
            max(v, -maxLength)
        }
    }

}