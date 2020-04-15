package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.entities.*
import org.neubauerfelix.manawars.game.events.EntityGoldEvent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

class CDEntityCastle(x: Float, y: Float, textureNameAlive: String, textureNameDead: String, health: Float,
                     direction: Int, team: Int, override val unitSpawnLocation: ILocated, startGold: Int,
                     goldPerCharge: Int) :
        MEntityAnimated(IEntityAnimationProducer.createProducerBuilding(textureNameAlive, textureNameDead), health),
        ICDEntityCastle {
    
    init {
        this.direction = direction
        this.team = team
        this.x = x
        this.y = y
    }


    override var goldPerCharge: Int = goldPerCharge
    private var nextGoldChargeTime = MManaWars.m.screen.getGameTime()

    override var gold: Int = startGold


    override var speedX: Float
        get() = super.speedX
        set(value) { }

    override var speedY: Float
        get() = super.speedX
        set(value) { }

    override fun draw(delta: Float, batcher: Batch) {
        super.draw(delta, batcher)
        MManaWars.m.getCharacterBarHandler().drawStatsBar(batcher, this)
    }


    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (MManaWars.m.screen.getGameTime() >= nextGoldChargeTime) {
            nextGoldChargeTime = MManaWars.m.screen.getGameTime() +
                    (1000 * CDConstants.CASTLE_GOLD_CHARGE_DELAY).toLong()
            val event = EntityGoldEvent(this, this, goldPerCharge)
            MManaWars.m.getEventHandler().callEvent(event)
            if (!event.cancelled) {
                event.castle.gold += event.goldDifference
            }
        }
    }


}