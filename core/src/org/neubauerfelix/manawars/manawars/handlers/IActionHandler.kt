package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.storage.Configuration

interface IActionHandler: IHandler {



    fun loadAction(name: String, configuration: Configuration) : IDataAction

}