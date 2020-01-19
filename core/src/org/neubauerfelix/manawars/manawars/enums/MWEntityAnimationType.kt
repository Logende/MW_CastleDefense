package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.units.DataUnitDummy
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanSmart
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerDummy


enum class MWEntityAnimationType(val width: Int, val height: Int) {

    HUMAN(MConstants.BODY_HUMAN_WIDTH, MConstants.BODY_HUMAN_HEIGHT) {
        override fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled {
            val skinName = "l1.zombie.mage"
            val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    HUMAN_SHIELD(MConstants.BODY_HUMAN_WIDTH, MConstants.BODY_HUMAN_HEIGHT) {
        override fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled {
            val skinName = "dummy.shield"
            val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            require(a.animation.entityAnimationType == HUMAN_SHIELD)
            require(((a.animation as EntityAnimationAny).body as BodyHumanSmart).shield != null)
            a.animation.body.doLogic(Float.MAX_VALUE) // causes animation to call #nextPosition and enables shield
            return a
        }
    },
    MOUNT(MConstants.BODY_MOUNT_WIDTH, MConstants.BODY_MOUNT_HEIGHT) {
        override fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled {
            val skinNameMount = "lion"
            val animationProducerMount = IEntityAnimationProducer.createProducerMount(skinNameMount)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducerMount, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    RIDER(MConstants.BODY_RIDER_WIDTH, MConstants.BODY_RIDER_HEIGHT) {
        override fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled {
            val skinName = "l1.zombie.mage"
            val skinNameMount = "lion"
            val animationProducerRider = IEntityAnimationProducer.createProducerRider(skinNameMount, skinName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducerRider, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    CASTLE(MConstants.BODY_CASTLE_WIDTH, MConstants.BODY_CASTLE_HEIGHT) {
        override fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled {
            val textureName = "castles/castle_wood_1.png"
            MManaWars.m.getAssetLoader().loadTexture(textureName)
            while (! MManaWars.m.getAssetLoader().areAssetsLoaded()){
                // Do nothing
            }
            val animationProducer = IEntityAnimationProducer.createProducerCastle(textureName, textureName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    },
    BUILDING_HEAL(MConstants.BODY_CASTLE_WIDTH, MConstants.BODY_CASTLE_HEIGHT) {
        override fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled {
            val textureName = "castles/castle_wood_1.png"
            MManaWars.m.getAssetLoader().loadTexture(textureName)
            while (! MManaWars.m.getAssetLoader().areAssetsLoaded()){
                // Do nothing
            }// TODO: Change castle code here into heal code
            val animationProducer = IEntityAnimationProducer.createProducerCastle(textureName, textureName)
            val controller = ControllerDummy()
            val a = MEntityControlled(animationProducer, 1f, action, 0f, controller = controller,
                    data = DataUnitDummy())
            controller.controlled = a
            a.setLocation(x, y)
            a.team = MConstants.TEAM_BOT
            a.direction = direction
            a.animation.updateAnimation(a)
            return a
        }
    };


    abstract fun createDummy(x: Float, y: Float, action: IDataAction, direction: Int) : IControlled
}