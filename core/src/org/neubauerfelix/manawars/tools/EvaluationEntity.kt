package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled

class EvaluationEntity(private val config: IEvaluationConfig) : GameEntity(0f, 0f), ILogicable {

    override fun doLogic(delta: Float) {
        val screen = MManaWars.m.screen
        if (screen.getGameTime() > config.maxMatchLength ||
                screen.getEntities { it is MEntityControlled }.count() > config.maxUnitsCount) {
            screen.remove = true
        }
    }


}