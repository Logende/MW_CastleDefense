package org.neubauerfelix.manawars.castledefense.data.buildings

interface IDataBuildingHeal : IDataBuilding {

    val healingPower: Float // health to heal
    val cooldown: Long
    val range: Float
}