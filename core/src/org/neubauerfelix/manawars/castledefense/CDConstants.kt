package org.neubauerfelix.manawars.castledefense

class CDConstants {

    companion object {
        const val CASTLE_BORDER_OFFSET = 20f
        const val FORMATION_UNIT_DISTANCE = -30f // does not include unit width
        const val FORMATION_JOIN_DISTANCE = 100f // does not include unit width
        const val FORMATION_ENEMY_DISTANCE_MIN = 300f
        const val CASTLE_GOLD_CHARGE_DELAY = 5f
        const val CASTLE_CHOOSE_UNIT_DELAY = 4000L

        const val CASTLE_HEALTH_BASE = 500f
        const val CASTLE_GOLD_START_BASE = 80f
        const val CASTLE_GOLD_PER_SECOND_BASE = 4f
        
    }
}