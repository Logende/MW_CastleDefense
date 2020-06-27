package org.neubauerfelix.manawars.castledefense.profile

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataCastle
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

class PlayerCastle() : IDataCastle {

    override val name: String = "player_castle"

    lateinit var enemyCastle: IDataCastle


    override val unitSpawnXOffset: Float
        get() = enemyCastle.unitSpawnXOffset
    override val unitSpawnYOffset: Float
        get() = enemyCastle.unitSpawnYOffset
    override val moneyStart: Int
    get() = enemyCastle.moneyStart
    override val moneyPerCycle: Int
    get() = enemyCastle.moneyPerCycle
    override val health: Float
    get() = enemyCastle.health
    override val xOffset: Float
    get() = enemyCastle.xOffset
    override val yOffset: Float
    get() = enemyCastle.yOffset

    override fun getTextureName(theme: MWBackgroundTheme): String {
        return enemyCastle.getTextureName(theme)
    }
}