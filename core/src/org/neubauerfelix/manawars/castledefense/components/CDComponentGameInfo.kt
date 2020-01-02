package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.MComponent
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class CDComponentGameInfo(x: Float, y: Float, val player: ICDPlayer) :
        MComponent(x, y, 200f, 200f) {


    private val goldPlayer = MTextLabel(x, y, "P: ${player.castle.gold} Gold",
            FontHandler.MWFont.TEXT, 1f)
    private val goldEnemy = MTextLabel(x, goldPlayer.bottom + MConstants.UI_DISTANCE_COLUMNS,
            "E: ${player.enemy.castle.gold} Gold", FontHandler.MWFont.TEXT, 1f)



    init {
        goldEnemy
    }

    override fun draw(delta: Float, batcher: Batch, offsetX: Float, offsetY: Float) {
        goldEnemy.text = "E: ${player.enemy.castle.gold} Gold"
        goldPlayer.text = "P: ${player.castle.gold} Gold"
        goldPlayer.draw(delta, batcher, offsetX, offsetY)
        goldEnemy.draw(delta, batcher, offsetX, offsetY)
    }

    override fun clickAction() {
    }

    override fun unclickAction() {
    }

}

