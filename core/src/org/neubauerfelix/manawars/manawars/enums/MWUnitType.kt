package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars

enum class MWUnitType(val defaultRange: Float, val main: Boolean, val index: Int) {

    // Note that the main units here need to be in the same order as the indices
    BOSS(1000f, true, 0),
    TANK(800f, true, 1),
    MELEE(700f, true, 2),
    RANGER(1900f, true, 3),
    MAGE(1900f, true, 4),
    BUILDER(0f, false, -1),
    BUILDING(1100f, false, -1);


    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("unittype_${name.toLowerCase()}")


}