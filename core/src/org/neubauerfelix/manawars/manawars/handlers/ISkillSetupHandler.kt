package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.entities.IActionUser

interface ISkillSetupHandler: IHandler {


    fun findTarget(data: IDataSkill, owner: IActionUser): IEntity?
    fun setupLocation(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?)
    fun setupMovement(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?)


}