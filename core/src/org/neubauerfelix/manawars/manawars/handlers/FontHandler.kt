package org.neubauerfelix.manawars.manawars.handlers


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.ILoadable


class FontHandler : IHandler, IDisposable, ILoadable {


    enum class MWFont private constructor(private val filename: String?, private val defaultScale: Float, private val defaultColor: Color? = null) {
        /** A huge white font used for headings and big text.  */
        MAIN("title", 2.5f),
        /** Normal text font.  */
        TEXT("text", 0.75f, Color.BLACK),
        /** Normal text font.  */
        PLAYGROUND_HINT("text", 0.5f, 0f, 0f, 0f, 0.5f),
        /** A small and rather normal font in red.  */
        PLAYGROUND(null, 2.0f, 128f, 0f, 0f, 0.8f),
        /** A medium-sized black font.  */
        HEADING("heading", 0.9f),
        /** A medium-sized darkred font with black outline. Bigger and with a more powerful color than the normal heading font.  */
        BIG_HEADING("big_heading", 0.9f);

        private var font: BitmapFont? = null

        constructor(filename: String?, defaultScale: Float, r: Float, g: Float, b: Float, a: Float) :
                this(filename, defaultScale, Color(r, g, b, a))

        fun load() {
            if (filename == null) {
                font = BitmapFont()
            } else {
                font = BitmapFont(Gdx.files.internal("fonts/$filename.fnt"))
            }
            if (defaultColor != null) {
                font!!.color = defaultColor
            }
            font!!.data.markupEnabled = true
            font!!.data.setScale(defaultScale, -defaultScale)
        }

        fun dispose() {
            font!!.dispose()
            font = null
        }

        @JvmOverloads
        fun getFont(scale: Float = 1f): BitmapFont {
            val newScale = defaultScale * scale
            if (font!!.scaleX != newScale) {
                font!!.data.setScale(newScale, -newScale)
            }
            return font!!
        }


    }


    override fun dispose() {
        for (font in MWFont.values()) {
            font.dispose()
        }
    }


    override fun load() {
        for (font in MWFont.values()) {
            font.load()
        }
    }

}
