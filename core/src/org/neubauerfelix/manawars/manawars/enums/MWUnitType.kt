package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars

enum class MWUnitType(val defaultRange: Float) {

    BOSS(1000f),
    TANK(800f),
    MELEE(700f),
    RANGER(1900f),
    MAGE(1900f),
    BUILDER(0f),
    BUILDING(1100f);


    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("unittype_${name.toLowerCase()}")


}