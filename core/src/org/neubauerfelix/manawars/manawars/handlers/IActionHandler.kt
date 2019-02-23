package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.skills.Skill
import org.neubauerfelix.manawars.manawars.enums.MWShield

interface IActionHandler: IHandler {



    fun putAction(action: IDataAction)
    fun getAction(name: String): IDataAction?
    fun getParent(action: IDataAction): IDataAction?
    fun listActions(): Iterable<IDataAction>

}