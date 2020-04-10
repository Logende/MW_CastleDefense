package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.units.DataUnitDummy
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanSmart
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerDummy


enum class MWEntityAnimationType {

    HUMAN {
        override fun createDummy(x: Float, bottom: Float, action: IDataAction, direction: Int) : IControlled {
            val skinName = "l1.zombie.mage"
            val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.x = x
            a.bottom = bottom
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    HUMAN_SHIELD {
        override fun createDummy(x: Float, bottom: Float, action: IDataAction, direction: Int) : IControlled {
            val skinName = "dummy.shield"
            val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.x = x
            a.bottom = bottom
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            require(a.animation.entityAnimationType == HUMAN_SHIELD)
            require(((a.animation as EntityAnimationAny).body as BodyHumanSmart).shield != null)
            a.animation.body.doLogic(Float.MAX_VALUE) // causes animation to call #nextPosition and enables shield
            return a
        }
    },
    MOUNT {
        override fun createDummy(x: Float, bottom: Float, action: IDataAction, direction: Int) : IControlled {
            val skinNameMount = "lion"
            val animationProducerMount = IEntityAnimationProducer.createProducerMount(skinNameMount)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducerMount, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.x = x
            a.bottom = bottom
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    RIDER {
        override fun createDummy(x: Float, bottom: Float, action: IDataAction, direction: Int) : IControlled {
            val skinName = "l1.zombie.mage"
            val skinNameMount = "lion"
            val animationProducerRider = IEntityAnimationProducer.createProducerRider(skinNameMount, skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducerRider, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.x = x
            a.bottom = bottom
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    BUILDING {
        override fun createDummy(x: Float, bottom: Float, action: IDataAction, direction: Int) : IControlled {
            val animationProducer = EntityAnimationProducerBuilding("building.heal")
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.x = x
            a.bottom = bottom
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    };


    // the dummy is used for the 'how much distance does a skill need to hit the entity' simulation
    abstract fun createDummy(x: Float, bottom: Float, action: IDataAction, direction: Int) : IControlled
}