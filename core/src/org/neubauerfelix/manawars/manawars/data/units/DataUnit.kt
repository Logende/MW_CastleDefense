package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect

abstract class DataUnit : IDataUnit {


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        return CDComponentUnit(x, y, width, height, this, action)
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
        if (MConstants.ALWAYS_EQUIP_WEAPONS) {
            val weaponType = entity.action.weaponType
            if (weaponType != null) {
                entity.animation.equipWeapon(weaponType)
            }
        }
        entity.spawn()
        return entity
    }
}