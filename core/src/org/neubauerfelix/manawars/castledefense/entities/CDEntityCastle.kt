package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType

class CDEntityCastle(x: Float, y: Float, val texture: TextureRegion, override var direction: Int, override var team: Int,
                     override val unitSpawnLocation: ILocated) :
        GameRectangle(x, y, texture.regionWidth.toFloat(), texture.regionHeight.toFloat()), ICDEntityCastle {




    override var gold: Int = 0

    override fun draw(delta: Float, batcher: Batch) {
        batcher.draw(texture, x, y, width, height)
    }


    override fun getCollisionType(other: ISized): MWCollisionType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var remove: Boolean = false
    override var propertyScale: Float = 1f

    override fun spawn() {
        MManaWars.m.screen.addEntity(this)
    }

}