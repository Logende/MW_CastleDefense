package org.neubauerfelix.manawars.game

class GameConstants {


    companion object {
        const val DEBUG_BOUNDING_BOXES = false

        const val GAME_RENDER_FIX_TIME_STEPS_DURATION = 1f/60f

        const val SCREEN_WIDTH = 1920f
        const val SCREEN_HEIGHT = 1080f
        const val BACKGROUND_WIDTH = 1920f
        const val BACKGROUND_HEIGHT = 880f
        const val CONTROLPANEL_HEIGHT = 220f
        const val CONTROLPANEL_TEXTURE = "controlpanel"
        const val CONTROLPANEL_BUTTON_SIZE = 180f
        const val CONTROLPANEL_BUTTON_BORDER = 40f
        const val CONTROLPANEL_BUTTON_DISTANCE = 20f

        const val WORLD_HEIGHT = SCREEN_HEIGHT - CONTROLPANEL_HEIGHT
        const val BACKGROUND_HEIGHT_CUT_OFF = BACKGROUND_HEIGHT - WORLD_HEIGHT

        const val PATH_ATLAS_BUTTONS = "components/buttons_small.pack"
        const val PATH_ATLAS_MAIN = "main/main.pack"
        const val PATH_ATLAS_SKILLS = "skills/skills.pack"
        const val PATH_BACKGROUND = "backgrounds/1/"
        const val PATH_LOADING_SCREEN = PATH_BACKGROUND + "loading.jpg"
    }
}