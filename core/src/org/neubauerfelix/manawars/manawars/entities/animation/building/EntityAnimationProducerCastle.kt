package org.neubauerfelix.manawars.manawars.entities.animation.building

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

class EntityAnimationProducerCastle(textureNameAlive: String, textureNameDead: String) :
        EntityAnimationProducerBuilding(textureNameAlive, textureNameDead, listOf(), 0f,
        MConstants.BODY_CASTLE_WIDTH, MConstants.BODY_CASTLE_HEIGHT, MWEntityAnimationType.CASTLE) {
}