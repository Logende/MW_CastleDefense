package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.entities.*
import org.neubauerfelix.manawars.castledefense.events.EntityGoldEvent
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimationSimple
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

class CDEntityMine(x: Float, y: Float,
                   animation: Animation<TextureRegion>,
                   override var chargePeriod: Float,
                   override var goldPerCharge: Int,
                   override val castle: ICDEntityCastle) :
        MEntityAnimationSimple(animation, 1f, null, 0f, Animation.PlayMode.NORMAL),
        ICDEntityMine {

    init {
        this.mirror = castle.direction == -1
        this.x = x
        this.y = y
    }


    private var nextGoldChargeTime = MManaWars.m.screen.getGameTime() + (1000 * chargePeriod).toLong()


    override var speedX: Float
        get() = super.speedX
        set(_) { }

    override var speedY: Float
        get() = super.speedX
        set(_) { }



    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (MManaWars.m.screen.getGameTime() >= nextGoldChargeTime) {
            nextGoldChargeTime = MManaWars.m.screen.getGameTime() +
                    (1000 * chargePeriod).toLong()
            // TODO: Instead of just giving the money, the human player should be forced to manually collect it
            val event = EntityGoldEvent(this, castle, goldPerCharge)
            MManaWars.m.getEventHandler().callEvent(event)
            if (!event.cancelled) {
                event.castle.gold += event.goldDifference
            }
        }
    }


}