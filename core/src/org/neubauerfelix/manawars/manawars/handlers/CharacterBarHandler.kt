package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.enums.MWArmorType
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.ILoadableAsync
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.ILiving


class CharacterBarHandler : ICharacterBarHandler, ILoadableAsync {


    private lateinit var characterBarStatus: TextureRegion
    private lateinit var characterBarFrame: TextureRegion

    private lateinit var signBoss: TextureRegion
    private lateinit var armorSymbol: TextureRegion
    private lateinit var frame: TextureRegion
    private lateinit var frameDefault: TextureRegion




    override fun isLoaded(): Boolean {
        return ::frame.isInitialized && ::frameDefault.isInitialized
    }

    override fun load() {
    }

    override fun loadedAssets() {
        if (! isLoaded()) {
            val imageHandler = MManaWars.m.getImageHandler()
            val characterBar = imageHandler.getTextureRegionMain("misc.characterbar")
            characterBar.flip(false, true) // Undo flipping to make extracting the different parts easier
            characterBarStatus = TextureRegion(characterBar, 0, 0, characterBar.regionWidth, characterBar.regionHeight/2)
            characterBarFrame = TextureRegion(characterBar, 0, characterBar.regionHeight/2, characterBar.regionWidth, characterBar.regionHeight/2)

            signBoss = imageHandler.getTextureRegionMain("symbol.boss")
            armorSymbol = imageHandler.getTextureRegionMain("symbol.armor")
            frame = imageHandler.getTextureRegionButton("frame")
            frameDefault = imageHandler.getTextureRegionButton("frame.default")
        }
    }


    override fun drawStatsBar(batcher: Batch, e: ILiving) {
        val width = Math.min(MConstants.INGAME_CHARACTERBAR_WIDTH,
                e.width + CDConstants.FORMATION_UNIT_DISTANCE)
        val height = MConstants.INGAME_CHARACTERBAR_HEIGHT

        val armor = if (e is IControlled) { e.data.armor } else { MWArmorType.NONE }
        this.drawStatsBar(batcher, e.health, e.healthMax, e.centerHorizontal, e.top - 5f, width, height, armor)
    }

    fun drawStatsBar(batcher: Batch, health: Float, maxHealth: Float, middleX: Float, bottom: Float, width: Float,
                     height: Float, armor: MWArmorType) {
        val x = middleX - width / 2f
        val y = bottom - height

        val healthRatio = health / maxHealth
        batcher.color = when {
            healthRatio >= 0.7 -> Color.GREEN
            healthRatio >= 0.5 -> Color.YELLOW
            healthRatio >= 0.3 -> Color.ORANGE
            else -> Color.RED
        }
        batcher.draw(characterBarStatus, x, y, width * healthRatio, height)
        batcher.draw(characterBarFrame, x, y, width, height)

        if (armor != MWArmorType.NONE) {
            val yArmor = y - 10f - armorSymbol.regionHeight
            batcher.color = armor.color
            batcher.draw(armorSymbol,
                    middleX - armorSymbol!!.regionWidth / 2,
                    yArmor)
        }
        batcher.color = Color.WHITE
    }

    override fun drawFrame(batcher: Batch, x: Float, y: Float, width: Float, height: Float,
                           color: Color?) {
        val w = width * frame.regionWidth / frame.regionWidth
        val h = height * frame.regionHeight / frame.regionHeight

        if (color == null) {
            batcher.draw(frameDefault, x, y , w, h)
        } else {
            batcher.color = color
            batcher.draw(frame, x, y , w, h)
            batcher.color = Color.WHITE
        }
    }
}