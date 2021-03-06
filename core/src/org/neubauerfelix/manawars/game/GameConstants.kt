package org.neubauerfelix.manawars.game

class GameConstants {


    companion object {
        // TODO: Outsource to configuration java object
        const val DEBUG_BOUNDING_BOXES = false
        const val GAME_RENDER_FIX_TIME_STEPS_DURATION = 1f/60f

        // can be changed for evaluation purposes:
        const val EVALUATION_MODE = false // for evaluation: package jar with this value set to true
        val FAST_MODE = EVALUATION_MODE || false // speeds game up
        val DISABLE_GRAPHICS = if (EVALUATION_MODE) true else false // disables graphics and audio and speeds game up
        val SPEED_FACTOR = if (FAST_MODE) 2f else 1f // does not change tick amount but delta per tick
        // does change simulation executions per tick (with fix delta)
        val GAME_TICK_FACTOR = if (EVALUATION_MODE) 50f else if (FAST_MODE) 5f else 1f
        val SLOW_INSTEAD_STUTTER = !FAST_MODE

        const val SCREEN_WIDTH = 1920f
        const val SCREEN_HEIGHT = 1080f
        const val BACKGROUND_WIDTH = 1920f
        const val BACKGROUND_HEIGHT = 1080f
        const val CONTROLPANEL_ON_TOP = true
        const val CONTROLPANEL_HEIGHT = 208f
        const val CONTROLPANEL_TEXTURE = "controlpanel"
        const val CONTROLPANEL_BUTTON_SIZE = 180f
        const val CONTROLPANEL_BUTTON_BORDER = 75f
        const val CONTROLPANEL_BUTTON_DISTANCE = 20f
        const val CONTROLPANEL_Y = 0f
        const val CONTROLPANEL_INFO_FIELD_BORDER = 55f
        const val CONTROLPANEL_INFO_FIELD_HEIGHT = CONTROLPANEL_HEIGHT - CONTROLPANEL_INFO_FIELD_BORDER / 2f
        const val CONTROLPANEL_INFO_FIELD_X = 1350f
        const val WORLD_HEIGHT_UNITS = SCREEN_HEIGHT - 35 // if controlpanel would be on bottom: minus control panel height
        const val WORLD_HEIGHT_BACKGROUNDS = SCREEN_HEIGHT


        const val PATH_ATLAS_BUTTONS = "components/buttons_small.pack"
        const val PATH_ATLAS_MAIN = "main/main.pack"
        const val PATH_ATLAS_SKILLS = "skills/skills.pack"
        const val PATH_BACKGROUND = "backgrounds/"
        const val PATH_LOADING_SCREEN = PATH_BACKGROUND + "loading.jpg"
    }
}