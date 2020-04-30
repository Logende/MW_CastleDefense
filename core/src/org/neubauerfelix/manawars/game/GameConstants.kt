package org.neubauerfelix.manawars.game

class GameConstants {


    companion object {
        // TODO: Outsource to configuration java object
        const val DEBUG_BOUNDING_BOXES = false
        const val FAST_MODE = true // disables graphics and audio and speeds game up
        const val EXIT_APP_ON_CASTLE_DEFEAT = true
        const val SPEED_FACTOR_DEFAULT = 1f
        const val SPEED_FACTOR_FAST_MODE = 1f // does not change tick amount but delta per tick
        const val GAME_TICK_FACTOR = 200f // does change simulation executions per tick (with fix delta)
        const val SLOW_INSTEAD_STUTTER = false
        const val GAME_RENDER_FIX_TIME_STEPS_DURATION = 1f/60f
        // tick factor 10 and fast_mode = true:
 // units A: 12.0. units B: 2.0. castle health A: 154.0 castle health B: 0.0
        // units A: 9.0. units B: 1.0. castle health A: 112.0 castle health B: 0.0
        // units A: 12.0. units B: 2.0. castle health A: 154.0 castle health B: 0.0
        // units A: 8.0. units B: 3.0. castle health A: 326.0 castle health B: 0.0
        // units A: 12.0. units B: 2.0. castle health A: 154.0 castle health B: 0.0
        // units A: 12.0. units B: 2.0. castle health A: 154.0 castle health B: 0.0
        // tick factor 20:
        // units A: 12.0. units B: 3.0. castle health A: 2.0 castle health B: 0.0
        // units A: 8.0. units B: 3.0. castle health A: 326.0 castle health B: 0.0
        // units A: 8.0. units B: 3.0. castle health A: 326.0 castle health B: 0.0
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        //tick factor 20 after changes of cosmetical maps
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // tick factor 21
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // tick factor 10
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0 -> Is the game deterministic now?
        // tick factor 100
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // tick factor 200
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // tick factor 500
        // units A: 12.0. units B: 1.0. castle health A: 226.0 castle health B: 0.0
        // tick factor 6 and fast mode = false:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 -> seems like drawing screws sth up
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0
        // tick factor 10:
        // units A: 9.0. units B: 2.0. castle health A: 274.0 castle health B: 0.0
        // units A: 5.0. units B: 11.0. castle health A: 0.0 castle health B: 106.0
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0
        // units A: 9.0. units B: 2.0. castle health A: 274.0 castle health B: 0.0
        // tick factor 30:
        // units A: 9.0. units B: 1.0. castle health A: 456.0 castle health B: 0.0
        // units A: 9.0. units B: 1.0. castle health A: 456.0 castle health B: 0.0
        // units A: 9.0. units B: 1.0. castle health A: 456.0 castle health B: 0.0
        // units A: 9.0. units B: 1.0. castle health A: 456.0 castle health B: 0.0
        // tick factor 31:
        // units A: 7.0. units B: 0.0. castle health A: 354.0 castle health B: 0.0
        // units A: 7.0. units B: 0.0. castle health A: 354.0 castle health B: 0.0 - time 244064
        // tick factor 32:
        // units A: 8.0. units B: 1.0. castle health A: 322.0 castle health B: 0.0 - time 222960
        // tick factor 32 after minor changes:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360
        // tick factor 35:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360
        // tick factor 30:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360
        // tick factor 10:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360
        // tick factor 30 and fast mode = true:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360
        // tick factor 300:
        // units A: 9.0. units B: 0.0. castle health A: 294.0 castle health B: 0.0 - time 265360

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