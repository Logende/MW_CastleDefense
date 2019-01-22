package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillLoaded
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

import java.util.HashMap
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.storage.ConfigurationDecoratorInheritance


class ActionHandler: IActionHandler, ILoadableContent {

    private val actions = HashMap<String, IDataAction>()
    private val parents = HashMap<IDataAction, IDataAction>()



    override fun putAction(action: IDataAction) {
        actions[action.name] = action
    }

    override fun getAction(name: String): IDataAction? {
        return actions[name]
    }

    override fun getParent(action: IDataAction): IDataAction? {
        return parents[action]
    }





    override fun loadContent(gameConfig: Configuration) {
        val handlerConfigNames = gameConfig.getStringList("actions")
        for(handlerConfigName in handlerConfigNames){
            val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
            this.loadAction(handlerConfig, null)
        }
    }


    fun loadAction(config: Configuration, parent: IDataAction?) {
        for (key in config.keys) {
            var section = ConfigurationDecoratorInheritance(config.getSection(key), config)
            val action = DataSkillLoaded(key, section)
            actions[key] = action
            System.out.println("loaded action $key")
            if (parent != null) {
                this.parents[action] = parent
            }

            if (section.contains("children")) {
                section = ConfigurationDecoratorInheritance(config.getSection("children"), section)
                loadAction(section, action)
            }
        }
    }
}
