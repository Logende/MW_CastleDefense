package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.controller.IController

abstract class DataUnit : IDataUnit {


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun produce(x: Float, y: Float, controller: IController, team: Int) : IControlled {
        val entity = MEntityControlled(this.animation,
                this.health,
                action,
                actionCooldown,
                this.stateMultipliers,
                this.skillMultipliers,
                this.skillDurabilityMultipliers,
                this.drainMultiplier,
                this.armor,
                this.walkSpeedMax,
                this.walkAcceleration,
                controller, this)
        controller.controlled = entity
        entity.setLocation(x, y)
        entity.team = team
        entity.gravity()
        entity.animation.updateAnimation(entity)
        entity.spawn()
        return entity
    }
}