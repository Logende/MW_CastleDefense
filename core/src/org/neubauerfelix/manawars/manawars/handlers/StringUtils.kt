package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.graphics.Color

class StringUtils {

    companion object {

        fun colorAsColorCode(color: Color) : String {
            return "[" + colorAsHexString(color) + "]"
        }

        fun colorAsHexString(color: Color) : String {
            return String.format("#%02x%02x%02x",
                    (color.r * 255f).toInt(),
                    (color.g * 255f).toInt(),
                    (color.b * 255f).toInt())
        }
    }
}