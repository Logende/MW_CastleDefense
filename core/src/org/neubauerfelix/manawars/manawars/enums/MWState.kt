package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.entities.IAnimated
import org.neubauerfelix.manawars.manawars.entities.IStateable

enum class MWState constructor(private val pics_name: String, private val columns: Int, val worth: Int //Defines state priorities and skill mana cost for the state (additonal cost = duration*worth).
) {
    BURNING("effect.state.burning", 3, 5) {
        override fun effect(s: IStateable, eff: Boolean) {
            s.damage(1.7f * if (eff) 2f else 1f, s.stateTrigger!!, MWDamageCause.STATEEFFECT)
        }

        override fun end(s: IStateable) {}
        override fun start(s: IStateable) {}
    },

    POISONED("effect.state.poisoned", 3, 6) {
        override fun effect(s: IStateable, eff: Boolean) {
            s.damage(2.5f * if (eff) 2f else 1f, s.stateTrigger!!, MWDamageCause.STATEEFFECT)
        }

        override fun end(s: IStateable) {}
        override fun start(s: IStateable) {}
    },

    SLAGGED("effect.state.slagged", 3, 8) {
        override fun effect(s: IStateable, eff: Boolean) {}

        override fun end(s: IStateable) {}

        override fun start(s: IStateable) {}
    },

    /**
     * While frozen entities are unable to walk / execute actions. They still can be moved by knockback and other external influences.
     */
    FROZEN("effect.state.frozen", 1, 9) {
        override fun effect(s: IStateable, eff: Boolean) {
            if (eff) {
                s.damage(0.5f, s.stateTrigger!!, MWDamageCause.STATEEFFECT)
            }
        }

        override fun end(s: IStateable) {
            if (s is IAnimated) {
                s.animation.paused = false
            }
        }

        override fun start(s: IStateable) {
            if (s.canWalk()) {
                s.speedX = 0f
            }
            if (s is IAnimated) {
                s.animation.paused = true
            }
        }
    };

    var animation: Animation<TextureRegion>? = null
        private set

    fun load() {
        val keyFrames = arrayOfNulls<TextureRegion>(columns)
        val texture = AManaWars.m.getImageHandler().getTextureRegionMain(pics_name)
        val width = texture.regionWidth / columns
        for (i in 0 until columns) {
            keyFrames[i] = TextureRegion(texture, i * width, 0, width, -texture.getRegionHeight())
        }
        this.animation = Animation<TextureRegion>(0.07f, *keyFrames)
        this.animation!!.playMode = PlayMode.LOOP
    }

    fun getWorth(s: IStateable): Int {
        return if (s.getStateEffectivity(this) === MWStateEffectivity.EFFECTIVE) {
            worth * 2
        } else worth
    }

    abstract fun effect(s: IStateable, eff: Boolean)
    abstract fun end(s: IStateable)
    abstract fun start(s: IStateable)
}
