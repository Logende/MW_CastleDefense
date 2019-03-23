package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.enums.MWArmorType
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.ILoadableAsync
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType


class CharacterBarHandler : ICharacterBarHandler, ILoadableAsync {


    private lateinit var characterBarStatus: TextureRegion
    private lateinit var characterBarFrame: TextureRegion

    private lateinit var signBoss: TextureRegion
    private lateinit var armorSymbol: TextureRegion
    private lateinit var frame: TextureRegion


    private val armorSymbols: MutableMap<MWEntityAnimationType, LinkedHashMap<MWArmorHolder,TextureRegion>> = hashMapOf()
    private val armorFrames: MutableMap<MWEntityAnimationType, LinkedHashMap<MWArmorHolder,TextureRegion>> = hashMapOf()


    override fun isLoaded(): Boolean {
        return armorSymbols.isNotEmpty()
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

            armorSymbol.flip(false, true) // Undo flipping to make extracting the different parts easier
            frame.flip(false, true) // Undo flipping to make extracting the different parts easier
            MWEntityAnimationType.values().forEach { animationType ->

                armorSymbols[animationType] = linkedMapOf()
                armorFrames[animationType] = linkedMapOf()
                var previousShare = 0f

                for (i in 0 until animationType.armorHolders.size) {
                    val armorHolder = animationType.armorHolders[i]
                    val share = animationType.armorHolderShares[i]

                    val heightSymbol = (armorSymbol.regionHeight * share).toInt()
                    val widthSymbol = armorSymbol.regionWidth
                    val ySymbol = (armorSymbol.regionHeight * previousShare).toInt()
                    val symbolPart = TextureRegion(armorSymbol, 0, ySymbol, widthSymbol, heightSymbol)
                    symbolPart.flip(false, true)
                    armorSymbols[animationType]!!.put(armorHolder, symbolPart)

                    val heightFrame = (frame.regionHeight * share).toInt()
                    val widthFrame = frame.regionWidth
                    val yFrame = (frame.regionHeight * previousShare).toInt()
                    val framePart = TextureRegion(frame, 0, yFrame, widthFrame, heightFrame)
                    framePart.flip(false, true)
                    armorFrames[animationType]!!.put(armorHolder, framePart)

                    previousShare += share
                }
            }
        }
    }


    override fun drawStatsBar(batcher: Batch, e: IControlled) {
        val width = MConstants.INGAME_CHARACTERBAR_WIDTH
        val height = MConstants.INGAME_CHARACTERBAR_HEIGHT
        this.drawStatsBar(batcher, e.health, e.data.health, e.centerHorizontal, e.top - 5f, width, height,
                e.entityAnimationType, e.data.armor)
    }

    fun drawStatsBar(batcher: Batch, health: Float, maxHealth: Float, middleX: Float, bottom: Float, width: Float, height: Float,
                     animationType: MWEntityAnimationType, armor: Map<MWArmorHolder, MWArmorType>) {
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

        if (armor.values.filter { it != MWArmorType.NONE }.isNotEmpty()) {
            val yArmor = y - 10f - armorSymbol.regionHeight
            var yOffset = 0f
            armorSymbols[animationType]!!.forEach { armorHolder, textureRegion ->
                val armorType = armor[armorHolder]!!
                batcher.color = armorType.color
                batcher.draw(textureRegion,
                        middleX - textureRegion!!.regionWidth / 2,
                        yArmor + yOffset)
                yOffset += textureRegion.regionHeight
            }
        }
        batcher.color = Color.WHITE
    }

    override fun drawArmorFrame(batcher: Batch, x: Float, y: Float, width: Float, height: Float,
                                animationType: MWEntityAnimationType, armor: Map<MWArmorHolder, MWArmorType>) {
        var yOffset = 0f
        armorFrames[animationType]!!.forEach { armorHolder, textureRegion ->
            val armorType = armor[armorHolder]!!
            batcher.color = armorType.color
            val w = width * textureRegion.regionWidth / frame.regionWidth
            val h = height * textureRegion.regionHeight / frame.regionHeight
            batcher.draw(textureRegion, x, y + yOffset, w, h)
            yOffset += h
        }
        batcher.color = Color.WHITE
    }
}