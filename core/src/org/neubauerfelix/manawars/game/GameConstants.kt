package org.neubauerfelix.manawars.game

class GameConstants {


    companion object {
        val DEBUG_BOUNDING_BOXES = false

        val GAME_RENDER_FIX_TIME_STEPS_DURATION = 1f/60f

        val SCREEN_WIDTH = 1920f
        val SCREEN_HEIGHT = 1080f
        val BACKGROUND_WIDTH = 1920f
        val BACKGROUND_HEIGHT = 880f
        val CONTROLPANEL_HEIGHT = 220f
        val WORLD_HEIGHT = SCREEN_HEIGHT - CONTROLPANEL_HEIGHT
        val BACKGROUND_HEIGHT_CUT_OFF = BACKGROUND_HEIGHT - WORLD_HEIGHT

        val PATH_ATLAS_BUTTONS = "components/buttons_small.pack"
        val PATH_ATLAS_MAIN = "main/main.pack"
        val PATH_BACKGROUND = "backgrounds/1/"
        val PATH_LOADING_SCREEN = PATH_BACKGROUND + "loading.jpg"
    }
}