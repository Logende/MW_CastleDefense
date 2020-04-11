package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataActionUser
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface ISkillStatsHandler: IHandler {


    fun generateStats(fileName: String, skills: Map<IDataSkill, Float>) // map of skill, maxPossibleRange

    fun readStats(data: IDataSkill): ISkillStats

    fun analyseSkills(actionUsers: Collection<IDataActionUser>)


}