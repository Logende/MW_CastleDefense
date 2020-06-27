package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

interface IDataCastle {

    val name: String
    val unitSpawnXOffset: Float
    val unitSpawnYOffset: Float
    val moneyStart: Int
    val moneyPerCycle: Int
    val health: Float
    val xOffset: Float
    val yOffset: Float

    fun getTextureName(theme: MWBackgroundTheme) : String





}