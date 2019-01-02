package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IOwned
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.entities.MEntityLiving
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

import java.util.Vector

class SpecialAnimationDamaging(animation: Animation<TextureRegion>, override var owner: IEntity?, private val damage: Float, variableDamage: Boolean) : SpecialAnimation(animation), IOwned {
    private val variableDamage = variableDamage //if true a number is generated via distance.



    override fun spawn() {
        super.spawn()
        val entities = AManaWars.m.screen.getEntities { e -> e is MEntityLiving && e.overlaps(this) && !ITeamable.isTeamed(e, this) }
        entities.forEach { e -> collideWithEnemyAction(MEntityLiving::class.java.cast(e)) }
    }

    private fun collideWithEnemyAction(l: MEntityLiving) {
        var killedEntity = false

        if (!this.variableDamage) {
            killedEntity = l.damage(this.damage, this, MWDamageCause.ANIMATION) //No knockback here.
        } else {
            val maxDistancePossible = (Math.sqrt(Math.pow((width / 2.0), 2.0) + Math.pow((height / 2.0), 2.0))).toFloat()
            val distance = l.getDistance(this.centerLocation)
            val damage = this.damage - this.damage * distance / maxDistancePossible
            killedEntity = l.damage(damage, this, MWDamageCause.ANIMATION)
            l.knockback((damage * 100f), 250f, if (l.centerHorizontal > centerHorizontal) 1 else -1) //TODO: Move the fix numerical values to constants class
        }
        if (killedEntity) {
            killedEntity(l)
        }
    }


    open fun killedEntity(l: MEntityLiving) {
    }


}