package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface ISkillStatsHandler: IHandler {


    fun generateStats(fileName: String, skills: Collection<IDataSkill>)

    fun readStats(data: IDataSkill): ISkillStats


}