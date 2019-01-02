package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimationSimple

open class SpecialAnimation

@JvmOverloads constructor(animation: Animation<TextureRegion>, filter: Color? = null) : MEntityAnimationSimple(animation, 1f, filter) {

    init {
        animation.playMode = PlayMode.NORMAL
    }


    override fun newAnimationStart() {
        remove = true
    }
}