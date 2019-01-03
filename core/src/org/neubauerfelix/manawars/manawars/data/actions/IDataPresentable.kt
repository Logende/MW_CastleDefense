package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.IComponent

interface IDataPresentable {


    fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent
    fun generateIcon(size: Int, action: Runnable): IComponent
}