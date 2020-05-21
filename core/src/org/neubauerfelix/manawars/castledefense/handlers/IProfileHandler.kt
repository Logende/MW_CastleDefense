package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.profile.PlayerProfile
import org.neubauerfelix.manawars.game.IHandler

interface IProfileHandler: IHandler {


    fun getProfile() : PlayerProfile


}