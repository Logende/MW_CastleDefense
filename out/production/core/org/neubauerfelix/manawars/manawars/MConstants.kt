package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class MConstants {

    companion object {
        //UI
        val UI_LINE_DISTANCE = 5f
        val UI_DISTANCE_COLUMNS = 30f
        val UI_DISTANCE_ROWS = 30f

        fun getVisualLineHeight(font: FontHandler.MWFont, scale: Float): Float{
            return Math.abs(font.getFont(scale).capHeight) + 6f
        }

        //physics
        val GRAVITY_ACCELERATION = -3500f
        val JUMP_SPEED_DEFAULT = 1200f
        val KNOCKBACK_MIRROR_DIRECTION_MIN_SPEED = 100f //If abs(speedX) >= this value: Direction of skill is knockback direction. Else direction is side of entity the skill is further away.
        val OUT_OF_WORLDBORDER_KNOCKBACK_POWER_X = 1500
        val OUT_OF_WORLDBORDER_KNOCKBACK_POWER_Y = 400

        //teams
        /**
         * No team: Members with team `-1` will be enemy with everyone (except members of TEAM_PEACEFUL).
         */
        val TEAM_ALONE = -1

        /**
         * Team of the player.
         */
        val TEAM_PLAYER = 1

        /**
         * Team of all regular bots and against the player.
         */
        val TEAM_BOT = 8

        /**
         * Team of bots. Allied with other bots and only attacks the player if attacked by the player.
         */
        val TEAM_BOT_NEUTRAL = 9


        /**
         * Peaceful team which is allied with everyone, even TEAM_ALONE.
         */
        val TEAM_PEACEFUL = 0


        //animation
        val HIT_RED_BLINK_DURATION = 100

        val RANDOM_ANIMATION_DESPAWN_TIME: Int
            get() = (3200f + Math.random() * 500f).toInt()

        val BODY_PART_DETACH_KNOCKBACK_FACTOR = 10f

        val BODY_PART_DETACH_MAX_ROTATION_ANGLE = 30f

        val BODY_PART_DETATCH_ROTATION_START_SPEED = 18.0f

        val BODY_PART_BLOOD_DELAY = 100

        val BODY_HUMAN_WIDTH = 100
        val BODY_HUMAN_HEIGHT = 162

        val SKIN_HUMAN_WIDTH = 100
        val SKIN_HUMAN_HEIGHT = 180

        val SHIELD_SCALE = 1.2f

        val HUMAN_ANIMATION_SPEED = 0.1f



    }
}