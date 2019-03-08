package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.*
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType

class CDEntityCastle(x: Float, y: Float, val texture: TextureRegion, override var direction: Int, override var team: Int,
                     override val unitSpawnLocation: ILocated, startGold: Int, goldPerCharge: Int) :
        GameRectangle(x, y, texture.regionWidth.toFloat(), texture.regionHeight.toFloat()), ICDEntityCastle, ILogicable {



    override var goldPerCharge: Int = goldPerCharge
    private var nextGoldChargeTime = MManaWars.m.screen.getGameTime()

    override var gold: Int = startGold

    override fun draw(delta: Float, batcher: Batch) {
        batcher.draw(texture, x, y, width, height)
    }

    override fun doLogic(delta: Float) {
        if (MManaWars.m.screen.getGameTime() >= nextGoldChargeTime) {
            nextGoldChargeTime = MManaWars.m.screen.getGameTime() +
                    (1000 * CDConstants.CASTLEDEFENSE_CASTLE_GOLD_CHARGE_DELAY).toLong()
            gold += goldPerCharge
        }
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