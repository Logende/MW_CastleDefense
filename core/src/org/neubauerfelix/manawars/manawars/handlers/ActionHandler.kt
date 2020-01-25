package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.data.actions.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.lang.RuntimeException


class ActionHandler: IActionHandler {


    override fun loadAction(name: String, config: Configuration) : IDataAction {
            val type = config.getString("type")
            val action: IDataAction = if (type.isEmpty() || type.equals("normal", ignoreCase = true)) {
                DataSkillLoaded(name, config)
            } else if (type.equals("mix", ignoreCase = true)) {
                DataSkillMixLoaded(name, config)
            } else {
                throw RuntimeException("Unknown action type $type.")
            }
            println("created action ${action.name}")
        return action
    }

}
