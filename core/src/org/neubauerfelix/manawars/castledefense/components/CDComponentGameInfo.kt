package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MComponentContainer
import org.neubauerfelix.manawars.manawars.components.MComponentTextLabelImages
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class CDComponentGameInfo(x: Float, y: Float, val player: ICDPlayer) :
        MComponentContainer(x, y) {

    private val coinTexture = MManaWars.m.getImageHandler().getTextureRegionMain("icon.money")

    private val goldPlayer = MComponentTextLabelImages(0f, 0f, "",
            FontHandler.MWFont.MAIN, 0.3f)
    private val goldEnemy = MTextLabel(0f, goldPlayer.bottom + MConstants.UI_DISTANCE_COLUMNS,
            "E: ${player.enemy.castle.gold} Gold", FontHandler.MWFont.TEXT, 1f)



    init {
        addComponent(goldPlayer)
    }

    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {
        goldPlayer.setText("icon.money# ${player.castle.gold}")
        super.draw(batcher, offsetX, offsetY)
    }


}

