package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.manawars.MConstants

class CDConstants {

    companion object {
        const val CASTLE_BORDER_OFFSET = 20f
        const val FORMATION_UNIT_DISTANCE = -30f // does not include unit width
        const val FORMATION_JOIN_DISTANCE = 100f // does not include unit width
        const val FORMATION_UNIT_WIDTH = -1f // MConstants.BODY_HUMAN_WIDTH.toFloat() // -1f results in dynamic width
        const val FORMATION_ENEMY_DISTANCE_MIN = 300f
        const val CASTLE_GOLD_CHARGE_DELAY = 5f
        const val CASTLE_CHOOSE_ACTION_DELAY = 500L

        const val CASTLE_HEALTH_BASE = 500f
        const val CASTLE_GOLD_START_BASE = 80f
        const val CASTLE_GOLD_PER_SECOND_BASE = 4f

        const val KI_BALANCING_ENABLED = true
        const val KI_BALANCING_MAX_HELP_FACTOR = 1.3
        const val KI_BALANCING_MIN_HELP_FACTOR = 1 / KI_BALANCING_MAX_HELP_FACTOR
        
    }
}