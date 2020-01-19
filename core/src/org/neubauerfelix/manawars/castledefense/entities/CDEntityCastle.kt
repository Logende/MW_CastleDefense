package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.entities.*
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



    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (MManaWars.m.screen.getGameTime() >= nextGoldChargeTime) {
            nextGoldChargeTime = MManaWars.m.screen.getGameTime() +
                    (1000 * CDConstants.CASTLEDEFENSE_CASTLE_GOLD_CHARGE_DELAY).toLong()
            gold += goldPerCharge
        }
    }


}