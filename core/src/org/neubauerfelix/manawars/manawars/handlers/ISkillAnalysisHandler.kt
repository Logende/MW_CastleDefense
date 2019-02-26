package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.actions.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWShield
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

interface ISkillAnalysisHandler: IHandler {


    fun analyse(skill: IDataSkill, entityAnimationType: MWEntityAnimationType): ISkillAnalysis


    fun analyseSkills(fileName: String)
    fun loadSkillAnalysis(data: IDataSkill): Map<MWEntityAnimationType, ISkillAnalysis>


}