package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction

interface IActionHandler: IHandler {



    fun putAction(action: IDataAction)
    fun getAction(name: String): IDataAction
    fun getParent(action: IDataAction): IDataAction?
    fun listActions(): Iterable<IDataAction>

}