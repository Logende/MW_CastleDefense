package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface ISkillAnalysisHandler: IHandler {


    fun analyse(skill: IDataSkill, entityAnimationType: MWEntityAnimationType): ISkillAnalysis


    fun analyseSkills(fileName: String)
    fun loadSkillAnalysis(data: IDataSkill): Map<MWEntityAnimationType, ISkillAnalysis>


}