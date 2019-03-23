package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.IControlled

interface ISkillSetupHandler: IHandler {


    fun findTarget(data: IDataSkill, owner: IActionUser): IEntity?
    fun setupLocation(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?)
    fun setupMovement(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?)

    /**
     * Use this method to define a target which the following #findTarget calls will return.
     * If set to null an actual target will be searched.
     */
    fun setDummyTarget(target: IControlled?)


}