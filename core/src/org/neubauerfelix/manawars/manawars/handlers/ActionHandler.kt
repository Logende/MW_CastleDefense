package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillLoaded
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillMixLoaded
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

import java.util.HashMap
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.storage.ConfigurationDecoratorInheritance
import java.lang.RuntimeException


class ActionHandler: IActionHandler, ILoadableContent {

    private val actions = HashMap<String, IDataAction>()
    private val parents = HashMap<IDataAction, IDataAction>()
    override var loadedContent: Boolean = false

    override fun putAction(action: IDataAction) {
        actions[action.name] = action
    }

    override fun getAction(name: String): IDataAction? {
        return actions[name]
    }

    override fun getParent(action: IDataAction): IDataAction? {
        return parents[action]
    }

    override fun listActions(): Iterable<IDataAction> {
        return actions.values
    }

    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true
            val handlerConfigNames = gameConfig.getStringList("actions")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
                this.loadAction(handlerConfig, null, null)
            }
        }
    }


    private fun loadAction(configActions: Configuration, configParentAction: Configuration?, parent: IDataAction?) {
        for (key in configActions.keys) {
            val sectionAction: Configuration = if (configParentAction == null) configActions.getSection(key) else ConfigurationDecoratorInheritance(configActions.getSection(key), configParentAction)
            val type = sectionAction.getString("type")
            val action: IDataAction
            if (type.isEmpty() || type.equals("normal", ignoreCase = true)) {
                action = DataSkillLoaded(key, sectionAction)
            } else if (type.equals("mix", ignoreCase = true)) {
                action = DataSkillMixLoaded(key, sectionAction)
            } else {
                throw RuntimeException("Unknown action type $type.")
            }
            actions[key] = action
            System.out.println("created action $key")
            if (parent != null) {
                this.parents[action] = parent
            }

            if ((sectionAction !is ConfigurationDecoratorInheritance && sectionAction.contains("children")) ||
                    (sectionAction is ConfigurationDecoratorInheritance && sectionAction.containsSelf("children"))) {
                val sectionActionChildren = sectionAction.getSection("children")
                loadAction(sectionActionChildren, sectionAction, action)
            }
        }
    }
}
