package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.data.IAsset

interface IDataLeague : IAsset {

    val name: String
    val tribes: List<IDataTribe> // possible enemy tribes
    val castles: List<IDataCastle> // castles the tribes can choose from

    fun getTribe(name: String): IDataTribe?
    fun getCastle(name: String): IDataCastle?


    // not used by units, but used by humans who define values of units
    val unitHealthAvg: Float
    val bossHealthAvg: Float
    val unitSkillDamageAvg: Float
    val bossSkillDamageAvg: Float
    val unitCostAvg: Float
    val bossCostAvg: Float
    val startGoldAvg: Float
    val goldPerSecondAvg: Float
    val castleHealthAvg: Float

}