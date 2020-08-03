package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.*
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

class CDEntityCastle(x: Float, y: Float,
                     textureNameAlive: String,
                     health: Float,
                     direction: Int,
                     team: Int,
                     override val unitSpawnLocation: ILocated,
                     startMoney: Int,
                     override var moneyPerCycle: Int,
                     override val unitCostPerCycle: Int,
                     override val player: ICDPlayer) :
        MEntityAnimated(IEntityAnimationProducer.createProducerBuilding(textureNameAlive), 0f, health),
        ICDEntityCastle {
    
    init {
        this.direction = direction
        this.team = team
        this.x = x
        this.y = y
        this.animation.updateAnimation(this)
    }



    override var storedMoney: Int = startMoney


    override fun draw(batcher: Batch) {
        super.draw(batcher)
        MManaWars.m.getCharacterBarHandler().drawStatsBar(batcher, this)
    }





}