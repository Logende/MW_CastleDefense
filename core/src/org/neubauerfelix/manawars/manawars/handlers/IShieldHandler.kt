package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.enums.MWShield

interface IShieldHandler: IHandler {


    fun getShield(name: String): MWShield
    fun getPlayerShield(enchanted: Boolean, big: Boolean): MWShield


}