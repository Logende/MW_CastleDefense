package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.actions.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.enums.MWShield

interface ISkillAnalysisHandler: IHandler {


    fun analyse(skill: IDataSkill): ISkillAnalysis


}