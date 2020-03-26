package org.neubauerfelix.manawars.manawars.enums

enum class MWBackgroundSubtheme(val id: Int) {

    DEFAULT(0),
    STONE_CASTLE(1),
    STONE_FORT(2),
    EXOTIC_HOUSE(3),
    SANDSTONE_FORT(4),
    DARKSTONE_CASTLE(5),
    DARKSTONE_FORT(6),
    ICE_FORT(7),
    WOOD_TOWER(8),
    STONE_TOWER(9),
    SHIPS_IN_WATER(10);


    companion object {
        fun byId(id: Int): MWBackgroundSubtheme? {
            return values().find { it.id == id }
        }
    }

}