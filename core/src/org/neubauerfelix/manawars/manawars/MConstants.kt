package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class MConstants {

    companion object {
        //UI
        const val UI_LINE_DISTANCE = 5f
        const val UI_DISTANCE_COLUMNS = 30f
        const val UI_DISTANCE_ROWS = 30f
        const val UI_DISTANCE_SMALL = 15f
        const val INGAME_CHARACTERBAR_WIDTH = 100f
        const val INGAME_CHARACTERBAR_HEIGHT = 10f

        fun getVisualLineHeight(font: FontHandler.MWFont, scale: Float): Float{
            return Math.abs(font.getFont(scale).capHeight) + 6f
        }

        const val MENU_BACKGROUND_IMAGE = "menu.background.png"
        const val MENU_ACTION_BUTTON_Y = 890f
        const val MENU_TITLE_Y = 70f
        const val MENU_CONTENT_WITH_HEADER_Y = 240f
        const val MENU_CONTENT_WITHOUT_HEADER_Y = 140f

        //physics
        const val GRAVITY_ACCELERATION = 3500f
        const val JUMP_SPEED_DEFAULT = 1200f
        const val KNOCKBACK_MIRROR_DIRECTION_MIN_SPEED = 100f //If abs(speedX) >= this const value: Direction of skill is knockback direction. Else direction is side of entity the skill is further away.

        //teams
        /**
         * No team: Members with team `-1` will be enemy with everyone (except members of TEAM_PEACEFUL).
         */
        const val TEAM_ALONE = -1

        /**
         * Team of the tribe.
         */
        const val TEAM_PLAYER = 1

        /**
         * Team of all regular bots and against the tribe.
         */
        const val TEAM_BOT = 8

        /**
         * Team of bots. Allied with other bots and only attacks the tribe if attacked by the tribe.
         */
        const val TEAM_BOT_NEUTRAL = 9


        /**
         * Peaceful team which is allied with everyone, even TEAM_ALONE.
         */
        const val TEAM_PEACEFUL = 0


        //animation
        const val HIT_RED_BLINK_DURATION = 100

        val RANDOM_ANIMATION_DESPAWN_TIME: Int
            get() = (3200f + Math.random() * 500f).toInt()

        const val BODY_PART_DETACH_KNOCKBACK_FACTOR = 10f

        const val BODY_PART_DETACH_MAX_ROTATION_ANGLE = 30f

        const val BODY_PART_DETATCH_ROTATION_START_SPEED = 18.0f

        const val BODY_PART_BLOOD_DELAY = 100

        const val BODY_HUMAN_WIDTH = 100
        const val BODY_HUMAN_HEIGHT = 162

        const val SKIN_HUMAN_WIDTH = 100
        const val SKIN_HUMAN_HEIGHT = 180

        const val SHIELD_SCALE = 1f
        const val ALWAYS_WEAR_SHIELD = true

        const val HUMAN_ANIMATION_SPEED = 0.1f

        const val SKIN_MOUNT_WIDTH = 176
        const val SKIN_MOUNT_HEIGHT = 158

        const val BODY_MOUNT_WIDTH = 212
        const val BODY_MOUNT_HEIGHT = 135

        const val SKIN_DRAGON_WIDTH = 176
        const val SKIN_DRAGON_HEIGHT = 158

        const val MOUNT_ANIMATION_SPEED = 0.1f

        const val RIDER_CENTRE_MOUNT_CENTRE_OFFSET_X = 0
        const val RIDER_BOTTOM_MOUNT_TOP_OFFSET_Y = 80

        const val BODY_RIDER_WIDTH = BODY_MOUNT_WIDTH
        const val BODY_RIDER_HEIGHT = BODY_MOUNT_HEIGHT - RIDER_BOTTOM_MOUNT_TOP_OFFSET_Y + BODY_HUMAN_HEIGHT

        const val ALWAYS_EQUIP_WEAPONS = true
        const val USE_ARMOR_MECHANIC = false

        // skills
        const val MAXIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT = 25f
        const val MINIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT_NO_KILL = 100f // Minimum damage dealt to the skill when it does not kill its target
        const val MINIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT_KILL = 12f // Minimum damage dealt to the skill when it does kill its target
        const val SKILLS_COLLIDE_WITH_EACH_OTHER = false
        const val SKILL_STATS_FILE = "skillanalysis.yml"
        val ANALYSE_SKILLS = true &&! GameConstants.FAST_MODE

        // units
        const val UNIT_ANALYSIS_FILE_NAME = "unitanalysis.yml"
        const val UNIT_AVG_WALK_SPEED_MAX = 300f
        const val UNIT_AVG_WALK_ACC = 600f
        const val UNIT_RIDER_SPEED_FACTOR = 1f
        const val UNIT_RIDER_ACC_FACTOR = 1f

        // misc
        const val HIT_VISUALIZATION_SHOW_ABOVE_HEAD = true




    }
}