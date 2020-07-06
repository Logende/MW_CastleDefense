package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.game.GameConstants


class CDConstants {

    companion object {
        const val STARTER_TRIBE = "bear"
        const val PLAYER_PROFILE_FILE_NAME = "mw_cd_profile.yml"

        const val CASTLE_BORDER_OFFSET = 20f
        const val FORMATION_UNIT_DISTANCE = -30f // does not include unit width
        const val FORMATION_JOIN_DISTANCE = 100f // does not include unit width
        const val FORMATION_UNIT_WIDTH = -1f // MConstants.BODY_HUMAN_WIDTH.toFloat() // -1f results in dynamic width
        const val FORMATION_ENEMY_DISTANCE_MIN = 300f
        const val FORMATION_ORDER_BY_UNIT_TYPE = false
        const val CASTLE_CHOOSE_ACTION_DELAY = 500L
        const val UNIT_BUILD_CYCLE_TIME = 1000L * 15L

        const val CASTLE_HEALTH_BASE = 1f
        const val CASTLE_MONEY_START_BASE = 1f
        const val CASTLE_MONEY_PER_CYCLE_BASE = 1f

        val KI_BALANCING_ENABLED = if (GameConstants.EVALUATION_MODE) false else true
        const val KI_BALANCING_MAX_HELP_FACTOR = 1.3
        const val KI_BALANCING_MIN_HELP_FACTOR = 1 / KI_BALANCING_MAX_HELP_FACTOR
        // percentage of health the castle needs to fall below to get unit support (boss is spawned)
        const val KI_BALANCING_CASTLE_SUPPORT_HEALTH = 0.33f

        const val UI_MENU_MAIN_USE_DETAILED_UNIT_ICONS = false
        const val UI_MENU_MAIN_UNIT_SCALE = 1.5f
        const val UI_MENU_EDIT_ARMY_ICON_SCALE = 1.5f
        const val UI_MENU_UNITINFO_BACKGROUND = "backgrounds/menu_unitinfo.png"
        const val UI_MENU_MAIN_BACKGROUND_TOP = "backgrounds/menu_main_top.png"
        const val UI_MENU_MAIN_BACKGROUND_BETWEEN = "backgrounds/menu_main_between.png"
        const val UI_MENU_MAIN_BACKGROUND_BETWEEN_COUNT = 14
        const val UI_MENU_UNITINFO_UNIT_PREVIEW_BOX_WIDTH = 800
        const val UI_MENU_UNITINFO_UNIT_PREVIEW_BOX_HEIGHT = 470
        const val UI_MENU_UNITINFO_ICON_SIZE = 200f
        
    }
}