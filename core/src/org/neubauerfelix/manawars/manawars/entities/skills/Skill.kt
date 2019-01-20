package org.neubauerfelix.manawars.manawars.entities.skills

import com.badlogic.gdx.graphics.g2d.Animation
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.*
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.enums.*




class Skill(val data: IDataSkill, val o: IActionUser): MEntityAnimationSimple(data.animation!!, data.textureScale, data.color, data.animationRotationDuration, Animation.PlayMode.LOOP), IOwned {

    override var owner: IEntity? = o

    var health: Int
    var direction: Int = 1
        set(value) {
            this.mirror = (value == -1)
            field = value
        }

    val idle: Boolean
        get() = this.idleTimeLeft > 0
    var idleTimeLeft: Float
    var lifeTimeLeft: Float

    val target: IEntity?

    var inactive: Boolean = false

    val active: Boolean
        get() = !this.idle && !this.inactive

    init {
        // Set up basic values
        health = data.skillStrength
        direction = o.direction * (if (data.startSpeedX > 0) 1 else -1)
        idleTimeLeft = data.idleTime
        lifeTimeLeft = data.lifeTime

        target = MManaWars.m.getSkillSetupHandler().findTarget( data, o)
        MManaWars.m.getSkillSetupHandler().setupLocation(this, data, o, target)
        if (! this.idle) {
            MManaWars.m.getSkillSetupHandler().setupMovement(this, data, o, target)
        }

        if (data.pickOneFrame) {
            this.setDrawRandomImageOnly()
        }

    }

    override fun spawn() {
        super.spawn()
        // TODO make sound
    }

    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (idle) {
            // Idle
            this.idleTimeLeft -= delta
            if (!this.idle) {
                MManaWars.m.getSkillSetupHandler().setupMovement(this, data, o, target)
            }
        } else {
            // Not Idle -> active

            if (data.stopOnGround && this.bottom < 0f) { // Stop moving when hitting ground
                this.speedY = 0f
                this.speedX = 0f
                this.accelerationX = 0f
                this.accelerationY = 0f
                this.bottom = 0f
            }

            if (data.targetEnemy) {
                if (data.targetSpeedX != 0f) {
                    this.speedX = if (target!!.centerHorizontal > this.centerHorizontal) data.targetSpeedX else -data.targetSpeedX
                }
                if (data.targetSpeedY != 0f) {
                    this.speedY = if (target!!.centerVertical > this.centerVertical) data.targetSpeedY else -data.targetSpeedY
                }
            }

            this.lifeTimeLeft -= delta
            if (this.lifeTimeLeft <= 0) {
                this.remove = true
            }
        }
    }

    fun collisionSkill(s: Skill) {
        val healthS = s.health
        s.damage(health, this)
        this.damage(healthS, s)
    }

    fun collisionEnemy(e: ILiving, collisionType: MWCollisionType) {
        if (!this.active) {
            return
        }


        var damageFactor = 1f
        //damageFactor *= e.getSkillEffectivity(getSkillClass(), collision_type) TODO

        if (o is IUpgraded) {
            damageFactor += o.getSkillMultiplier(data.skillClass)
        }

        if (damageFactor == 0f) { //If entity is immune to skill type
            knockbackSkill(e, MWSkillKnockbackReason.ARMOR)
            return
        }
        if (e.invincible) {
            if (data.skillClass !== MWSkillClass.SHIELD) {
                knockbackSkill(e, MWSkillKnockbackReason.INVINCIBLE)
            }
            return
        }


        // Knockback
        if (e is IJumpable) {
            val knockbackFactor = Math.pow(damageFactor.toDouble(), 0.3).toFloat() * data.knockbackFactor * this.propertyScale
            val knockbackX = Math.abs(speedX / 3) + 60
            val knockbackY = Math.abs(speedX / 3) + 50 // Was speedX before instead of speedY
            e.knockback(knockbackX * knockbackFactor, knockbackY * Math.abs(knockbackFactor), direction)
        }

        // Damage
        val damage = ((Math.random() * (data.damageMax - data.damageMin) + data.damageMin) * this.propertyScale).toFloat()
        val killed = e.damage(damage * damageFactor, this, MWDamageCause.SKILL)

        // State effect
        if (!killed) {
            if (data.stateEffect != null) {
                if (e is IStateable) {
                    e.setState(data.stateEffect!!, data.stateEffectDuration, this)
                }
            }
        }

        // Damage skill itself
        val skillDamage = Math.min(MConstants.MAXIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT, e.health.toInt())
        // Skill damage
        if (!killed) {
            this.damage(Math.max(skillDamage, MConstants.MINIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT_NO_KILL), e)
        } else {
            this.damage(skillDamage, e)
            //if (s.getEnemiesKilled() >= 2 && getSkillClass() !== MWSkillClass.SHIELD && collision_type === EntityAnimationHuman.COLLISION_TYPE_HEAD) {
              //  ManaWarsGame.g.getSlowMotionHandler().slowDown() TODO
        }
    }


    fun knockbackSkill(cause: IMovable, reason: MWSkillKnockbackReason) {
        this.inactive = true

        var dir = if (this.centerHorizontal > cause.centerHorizontal) 1 else -1
            if (this.speedY == 0f && this.accelerationY == 0f) {
                dir = -this.direction
            }
        direction = dir
    }


    fun damage(value: Int, damager: IEntity): Boolean {
        var i = value / propertyScale
        this.health -= i.toInt()
        if (this.health <= 0) {
            remove = true
            return true
        }
        return false
    }


    // TODO: Handle collisions and damage enemies when hitting


}