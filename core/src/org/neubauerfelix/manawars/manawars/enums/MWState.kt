package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.entities.IAnimatedLiving
import org.neubauerfelix.manawars.manawars.entities.IStateable

 // tactical damage is used for state priorities and possibly worth calculations of skills).
enum class MWState (private val imageName: String, private val columns: Int, val tacticalDamage: Float,
                    val color: Color) {
    BURNING("effect.state.burning", 3, 1.7f, Color.RED) {
        override fun effect(s: IStateable, eff: Boolean) {
            s.damage(1.7f * if (eff) 2f else 1f, s.stateTrigger!!, MWDamageCause.STATEEFFECT)
        }

        override fun end(s: IStateable) {}
        override fun start(s: IStateable) {}
    },

    POISONED("effect.state.poisoned", 3, 2.5f, Color.GREEN) {
        override fun effect(s: IStateable, eff: Boolean) {
            s.damage(2.5f * if (eff) 2f else 1f, s.stateTrigger!!, MWDamageCause.STATEEFFECT)
        }

        override fun end(s: IStateable) {}
        override fun start(s: IStateable) {}
    },

    SLAGGED("effect.state.slagged", 3, 4f, Color.PURPLE) {
        override fun effect(s: IStateable, eff: Boolean) {}

        override fun end(s: IStateable) {}

        override fun start(s: IStateable) {}
    },

    /**
     * While frozen entities are unable to walk / execute actions. They still can be moved by knockback and other external influences.
     */
    FROZEN("effect.state.frozen", 1, 2f, Color.CYAN) {
        override fun effect(s: IStateable, eff: Boolean) {
            if (eff) {
                s.damage(0.5f, s.stateTrigger!!, MWDamageCause.STATEEFFECT)
            }
        }

        override fun end(s: IStateable) {
            if (s is IAnimatedLiving) {
                s.animation.paused = false
            }
        }

        override fun start(s: IStateable) {
            if (s.canWalk()) {
                s.speedX = 0f
            }
            if (s is IAnimatedLiving) {
                s.animation.paused = true
            }
        }
    };

    var animation: Animation<TextureRegion>? = null
        private set

    fun load() {
        val keyFrames = arrayOfNulls<TextureRegion>(columns)
        val texture = AManaWars.m.getImageHandler().getTextureRegionMain(imageName)
        val width = texture.regionWidth / columns
        for (i in 0 until columns) {
            keyFrames[i] = TextureRegion(texture, i * width, 0, width, -texture.regionHeight)
        }
        this.animation = Animation<TextureRegion>(0.07f, *keyFrames)
        this.animation!!.playMode = PlayMode.LOOP
    }

    fun getWorth(victim: IStateable): Float {
        return if (victim.getStateEffectivity(this) === MWStateEffectivity.EFFECTIVE) {
            tacticalDamage * 2f
        } else tacticalDamage
    }

    abstract fun effect(s: IStateable, eff: Boolean)
    abstract fun end(s: IStateable)
    abstract fun start(s: IStateable)
}
