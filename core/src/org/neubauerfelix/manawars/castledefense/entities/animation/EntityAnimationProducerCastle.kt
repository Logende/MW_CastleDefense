package org.neubauerfelix.manawars.castledefense.entities.animation

import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

class EntityAnimationProducerCastle(textureNameAlive: String, textureNameDead: String) :
        EntityAnimationProducerBuilding(textureNameAlive, textureNameDead, listOf(), 0f,
        447, 383, MWEntityAnimationType.BUILDING) { // TODO: Dynamic size depending on actual image
}