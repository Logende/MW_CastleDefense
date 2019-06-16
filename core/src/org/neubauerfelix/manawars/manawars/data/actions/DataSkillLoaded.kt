package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration


open class DataSkillLoaded(override val name: String, config: Configuration, override val model: IDataSkillModel,
                           override val effect: IDataSkillEffect) :
        DataSkill(), IDataActionLook by model {

    override val actionDependencies: Array<IDataAction>

    init {
        val actionDependencies = mutableListOf<IDataAction>()
        for (name in config.getStringList("actionDependencies")) {
            val action = MManaWars.m.getActionHandler().getAction(name)
            require(action != null)
            actionDependencies.add(action!!)
        }
        this.actionDependencies = actionDependencies.toTypedArray()
    }

    var analysis: Map<MWEntityAnimationType, ISkillAnalysis> = MManaWars.m.getSkillAnalysisHandler().loadSkillAnalysis(this)
        private set


    fun analyseSkill() {
        this.loadAsset()
        do {
            // wait
        } while (!AManaWars.m.getAssetLoader().areAssetsLoaded())
        this.loadedAsset()
        val analysis: MutableMap<MWEntityAnimationType, ISkillAnalysis> = hashMapOf()
        MWEntityAnimationType.values().forEach { type ->
            analysis[type] = MManaWars.m.getSkillAnalysisHandler().analyse(this, type)
        }
        this.analysis = analysis
        this.disposeAsset()
    }


    final override val lifeTime: Float
        get() =  analysis.getValue(MWEntityAnimationType.HUMAN).lifeTime


    override fun getActionProperties(entityAnimationType: MWEntityAnimationType) : IDataActionProperties {
        return analysis.getValue(entityAnimationType)
    }


}