package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWSkillKnockbackReason

class MEntityDestructiveWave(xCentre: Float, yCentre: Float, widthInitial: Float, heightInitial: Float, duration: Int, widthFinal: Float,
                             heightFinal: Float, override var owner: IEntity) :
        MEntityAnimationSimple(Animation(Float.MAX_VALUE,
                MManaWars.m.getImageHandler().getTextureRegionMain("aura")),
                1.0f, Color.WHITE, 0f, Animation.PlayMode.NORMAL), IOwned {

    private val xCentre: Float
    private val yCentre: Float
    private val widthInitial: Float
    private val heightInitial: Float
    private val widthFinal: Float
    private val heightFinal: Float
    private val timeStart: Long
    private val timeEnd: Long

    override fun doLogic(delta: Float) {
        updateLocation()
    }

    private fun updateLocation() {
        val progressTotal = timeEnd - timeStart
        val progressLeft: Long = timeEnd - MManaWars.m.screen.getGameTime()
        if (progressLeft <= 0) {
            remove = true
            return
        }
        val progressDone = progressTotal - progressLeft
        val progressPercentage = 1.0f * progressDone / progressTotal
        val width = widthInitial + ((widthFinal - widthInitial) * progressPercentage)
        val height = heightInitial + ((heightFinal - heightInitial) * progressPercentage)
        val x = xCentre - width / 2f
        val y = yCentre - width / 2f
        setSize(width, height)
        setLocation(x, y)
    }

    fun collide(s: MSkill) {
        if (s.active) {
            s.knockbackSkill(this, MWSkillKnockbackReason.INVINCIBLE)
        }
    }

    init {
        val screen = MManaWars.m.screen
        timeStart = screen.getGameTime()
        timeEnd = timeStart + duration
        this.owner = owner
        this.xCentre = xCentre
        this.yCentre = yCentre
        this.widthInitial = widthInitial
        this.heightInitial = heightInitial
        this.widthFinal = widthFinal
        this.heightFinal = heightFinal
        setSize(widthInitial, heightInitial)
        //Check collision with skills instantly to remove skills the wave/player is touching right now.
        //That is done because when entities land on ground and release a wave their invincibility is turned off instantly.
        //Skills that are touching them in that exact moment need to be destroyed, else they damage the player in the same tick the wave
        //would destroy them
        updateLocation()
        val entities = screen.getEntities { it is MSkill }
        synchronized(entities) {
            for (e in entities) {
                val s = e as MSkill
                if (!ITeamable.isTeamed(owner, s)) {
                    if (ISized.overlaps(s, owner)) {
                        collide(s)
                    }
                }
            }
        }
        // MManaWars.m.getSoundHandler().playSound("misc.aura", centerHorizontal) TODO
    }
}