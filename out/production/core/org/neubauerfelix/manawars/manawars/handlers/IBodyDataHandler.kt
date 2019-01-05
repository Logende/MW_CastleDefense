package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.entities.animation.human.IBodyDataHuman
import org.neubauerfelix.manawars.manawars.entities.animation.mount.IBodyDataMount

interface IBodyDataHandler: IHandler {


    fun getBodyDataHuman(name: String): IBodyDataHuman
    fun getBodyDataMount(name: String): IBodyDataMount


}