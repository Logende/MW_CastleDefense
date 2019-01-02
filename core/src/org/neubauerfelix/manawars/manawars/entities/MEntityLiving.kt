package org.neubauerfelix.manawars.manawars.entities


import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

/**
 * This class gives entities health, the ability to take damage/die and the ability to belong to a certain team.
 * @author Felix Neubauer
 */
abstract class MEntityLiving(width: Float, height: Float, health: Float) : MEntityJumpable(width, height), ILiving, ITeamable, IAttacking {

    companion object {
        fun getClosestEntity(condition: (IEntity) -> Boolean, mainEntity: IEntity, maxDistance: Double): IEntity?{
            var closestDistance = maxDistance
            var closestEntity: IEntity? = null
            val entities = AManaWars.m.screen.getEntities(condition)
            for (entity in entities){
                val distance = entity.getDistanceHor(mainEntity)
                if(distance <= closestDistance){
                    closestDistance = distance
                    closestEntity = entity
                }
            }
            return closestEntity
        }
    }



    final override var health = health
        private set(value) {
            require(health in 0f .. healthMax)
            this.health = value
        }

    var healthMax: Float = health
        private set(value) {
            this.healthMax = value
            if (this.health > this.healthMax)
                this.health = this.healthMax
        }

    var bloodColor: Color = Color.FIREBRICK
        set(c) {
            this.bloodColor = c
        }

    override var team = -1

    override val invincible: Boolean
        get() = false



    /**
     * Respawns the entity.
     * @param healthPercentage New health percentage. `1.00` equals maximum health.
     * @pre Requires the entity to be despawned.
     */
    fun respawn(healthPercentage: Float) {
        check(remove)
        require(healthPercentage in 0f .. 1f)

        remove = false
        if (!AManaWars.m.screen.containsEntity(this)) {
            //TODO EVENT?
            spawn()
        }
        health = healthMax * healthPercentage
    }

    override fun heal(value: Float) {
        if (health > 0) {
            this.health = Math.min(this.health + value, this.healthMax)
            //TODO EVENT?
        }
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        if (remove || value == 0f) {
            return false
        }
        require(value > 0)

        if (damager is IAttacking) {
            (damager as IAttacking).dealtDamage(this, Math.min(value, health), cause)
        }
        health = Math.max(0f, this.health - value)
        //TODO EVENT?
        playBloodAnimation()

        return if (this.health == 0f) {
            death(damager, cause)
        } else false
    }

    open fun playBloodAnimation() {
        //TODO
    }

    override fun spawn() {
        super.spawn()
        //TODO EVENT?
    }

    /**
     * Kills the entity: Despawns it.
     * @param damager Entity who killed this living entity.
     * @param cause Reason why damage was caused.
     * @return `true` if the entity was successfully killed and `false` if something prevented the death (for example a resurrect potion).
     */
    open fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        //TODO EVENT?
        remove = true
        if (damager is IAttacking) {
            (damager as IAttacking).killed(this, cause)
        }
        return true
    }

    override fun killed(victim: ILiving, cause: MWDamageCause) {
        if (this is IOwned) {
            val owner = this.owner
            if (owner is IAttacking) {
                owner.killed(victim, cause)
            }
        }
    }

    override fun dealtDamage(l: ILiving, damage: Float, cause: MWDamageCause) {
        if (this is IOwned) {
            val owner = this.owner
            if (owner is IAttacking) {
                owner.dealtDamage(l, damage, cause)
            }
        }
    }




    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        //TODO: CALL EVENT WHEN OUTSIDE WORLD BORDER?
        //if (right > GameCamera.getBorderEnd() || x < GameCamera.getBorderStart()) {
        //    if (outsideOfWorldBorder(x + getWidth() > GameCamera.getBorderEnd())) {
        //        setRemove(true)
        //    }
        //    return
        //}
    }




}