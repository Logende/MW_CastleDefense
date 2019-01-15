package org.neubauerfelix.manawars.manawars.entities


import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.EntityDamageEvent
import org.neubauerfelix.manawars.game.events.EntityDeathEvent
import org.neubauerfelix.manawars.game.events.EntityHealEvent
import org.neubauerfelix.manawars.game.events.EntitySpawnEvent
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

/**
 * This class gives entities health, the ability to take damage/die and the ability to belong to a certain team.
 * @author Felix Neubauer
 */
abstract class MEntityLiving(width: Float, height: Float, health: Float) : MEntityJumpable(width, height), ILiving, ITeamable {

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
            field = value
        }

    var healthMax: Float = health
        private set(value) {
            field = value
            if (this.health > this.healthMax)
                this.health = this.healthMax
        }

    var bloodColor: Color = Color.FIREBRICK

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
            AManaWars.m.getEventHandler().callEvent(EntitySpawnEvent(this, true, healthPercentage))
            spawn()
        }
        health = healthMax * healthPercentage
    }

    override fun heal(value: Float) {
        require(value > 0)

        val healEvent = EntityHealEvent(this, value)
        AManaWars.m.getEventHandler().callEvent(healEvent)
        if (!healEvent.cancelled) {
            this.health = Math.min(this.health + value, this.healthMax)
        }
    }

    override fun damage(damage: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        if (remove || damage == 0f) {
            return false
        }
        require(damage > 0)

        health = Math.max(0f, this.health - damage)
        val damageEvent = EntityDamageEvent(this, damage, damager, cause, this.health == 0f)
        AManaWars.m.getEventHandler().callEvent(damageEvent)

        if (damageEvent.cancelled) {
            return false
        }

        playBloodAnimation()
        return if (damageEvent.deadlyDamage) {
            this.death(damager, cause)
        } else {
            false
        }
    }

    open fun playBloodAnimation() {
        //TODO
    }

    override fun spawn() {
        super.spawn()
        AManaWars.m.getEventHandler().callEvent(EntitySpawnEvent(this, false, 1f))
    }

    /**
     * Kills the entity: Despawns it.
     * @param damager Entity who killed this living entity.
     * @param cause Reason why damage was caused.
     * @return `true` if the entity was successfully killed and `false` if something prevented the death (for example a resurrect potion).
     */
    open fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        val deathEvent = EntityDeathEvent(this, damager, cause)
        AManaWars.m.getEventHandler().callEvent(deathEvent)
        if (deathEvent.cancelled) {
            return false
        }
        remove = true
        return true
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