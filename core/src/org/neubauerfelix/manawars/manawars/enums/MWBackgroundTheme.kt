package org.neubauerfelix.manawars.manawars.enums

enum class MWBackgroundTheme(val id: Int) {

    GRASSLAND(0),
    DESERT(1),
    EXOTIC_GRASSLAND(2),
    SNOW(3),
    DARK_WORLD(4),
    COAST(5);

    companion object {
        fun byId(id: Int): MWBackgroundTheme? {
            return values().find { it.id == id }
        }
    }


}