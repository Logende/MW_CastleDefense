package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.entities.*
import org.neubauerfelix.manawars.castledefense.events.EntityGoldEvent
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

class CDEntityCastle(x: Float, y: Float,
                     textureNameAlive: String, textureNameDead: String,
                     health: Float,
                     direction: Int,
                     team: Int,
                     override val unitSpawnLocation: ILocated,
                     startGold: Int,
                     override var goldPerCharge: Int,
                     override val player: ICDPlayer) :
        MEntityAnimated(IEntityAnimationProducer.createProducerBuilding(textureNameAlive, textureNameDead), health),
        ICDEntityCastle {
    
    init {
        this.direction = direction
        this.team = team
        this.x = x
        this.y = y
        this.animation.updateAnimation(this)
    }


    private var nextGoldChargeTime = MManaWars.m.screen.getGameTime() +
            (1000 * CDConstants.CASTLE_GOLD_CHARGE_DELAY).toLong()

    override var gold: Int = startGold


    override var speedX: Float
        get() = super.speedX
        set(_) { }

    override var speedY: Float
        get() = super.speedX
        set(_) { }

    override fun draw(batcher: Batch) {
        super.draw(batcher)
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

    override fun destroyed() {
        super.destroyed()
        if (GameConstants.FAST_MODE) {
            Gdx.app.exit()
            println("winner is team ${player.enemy.team} with tribe ${player.enemy.tribe.name}")
        } else {
            // TODO
        }
    }


}