package org.neubauerfelix.manawars.manawars.enums

enum class MWBackgroundTheme(val id: Int) {

    GRASSLAND(0),
    DESERT(1),
    GRASSLAND_WOODEN_WALL(2),
    DESERT_WOODEN_WALL(3),
    GRASSLAND_STONE_WALL(4),
    GRASSLAND_DARKSTONE_WALL(5),
    EXOTIC_GRASSLAND(7),
    SNOW(8),
    SNOW_STONE_WALL(9),
    EXOTIC_GRASSLAND_SANDSTONE_WALL(11),
    COAST(12);

    companion object {
        fun byId(id: Int): MWBackgroundTheme? {
            return values().find { it.id == id }
        }
    }


}