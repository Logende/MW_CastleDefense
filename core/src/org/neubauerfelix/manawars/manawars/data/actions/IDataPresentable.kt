package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.IComponent

interface IDataPresentable {


    fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent
    fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent
}