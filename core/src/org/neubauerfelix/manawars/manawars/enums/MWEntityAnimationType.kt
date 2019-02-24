package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerDummy

// Share is the estimated percentage of units with this animation type from all battles. It is used for the skill price calculation.
enum class MWEntityAnimationType(val width: Int, val height: Int, val share : Float) {

    HUMAN(MConstants.BODY_HUMAN_WIDTH, MConstants.BODY_HUMAN_HEIGHT, 0.9f) {
        override fun createDummy(x: Float, y: Float, action: IDataAction) : IControlled {
            val skinName = "dwarf.1.1"
            val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0L, controller = controller)
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.animation.updateAnimation(a)
            return a
        }
    },
    MOUNT(MConstants.BODY_MOUNT_WIDTH, MConstants.BODY_MOUNT_HEIGHT, 0f) {
        override fun createDummy(x: Float, y: Float, action: IDataAction) : IControlled {
            val skinNameMount = "lion"
            val animationProducerMount = IEntityAnimationProducer.createProducerMount(skinNameMount)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducerMount, 1f, action, 0L, controller = controller)
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.animation.updateAnimation(a)
            return a
        }
    },
    RIDER(MConstants.BODY_RIDER_WIDTH, MConstants.BODY_RIDER_HEIGHT, 0.1f) {
        override fun createDummy(x: Float, y: Float, action: IDataAction) : IControlled {
            val skinName = "dwarf.1.1"
            val skinNameMount = "lion"
            val animationProducerRider = IEntityAnimationProducer.createProducerRider(skinNameMount, skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducerRider, 1f, action, 0L, controller = controller)
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.animation.updateAnimation(a)
            return a
        }
    };


    abstract fun createDummy(x: Float, y: Float, action: IDataAction) : IControlled
}