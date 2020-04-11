package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.enums.*
import kotlin.math.max
import kotlin.math.min


class MSkill(val data: IDataSkill, val o: IActionUser): MEntityAnimationSimple(data.animation!!, data.textureScale,
        data.color, data.animationRotationDuration * o.direction, Animation.PlayMode.LOOP), IOwned {

    override var owner: IEntity = o

    var health: Float = data.damage
    var direction: Int = o.direction
        set(value) {
            this.mirror = (value == -1)
            field = value
        }

    init {
        this.mirror = (o.direction == -1)
    }

    private val idle: Boolean
        get() = this.idleTimeLeft > 0
    var idleTimeLeft: Float = data.idleTime
    var lifeTimeLeft: Float = data.lifeTime

    private val target: IEntity? = MManaWars.m.getSkillSetupHandler().findTarget( data, o)

    var inactive: Boolean = false

    val active: Boolean
        get() = !this.idle && !this.inactive

    init {
        // Set up basic values
        MManaWars.m.getSkillSetupHandler().setupLocation(this, data, o, target)
        if (! this.idle) {
            startMoving()
        }

        if (data.pickOneFrame) {
            this.setDrawRandomImageOnly()
        }
    }

    private fun startMoving() {
        MManaWars.m.getSkillSetupHandler().setupMovement(this, data, o, target)
        data.soundPath?.let { MManaWars.m.getSoundHandler().playSound(data.soundPath!!, this.centerHorizontal) }
    }


    override fun draw(delta: Float, batcher: Batch) {
        if (! (idle &&data.invisibleWhileIdle)) {
            super.draw(delta, batcher)
        }
    }

    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (idle) {
            // Idle
            this.idleTimeLeft -= delta
            if (!this.idle) {
                startMoving()
            }
        } else {
            // Not Idle -> active

            if (data.stopOnGround && this.bottom > GameConstants.WORLD_HEIGHT) { // Stop moving when hitting ground
                this.speedY = 0f
                this.accelerationY = 0f
                this.bottom = GameConstants.WORLD_HEIGHT
            }

            if (data.targetEnemy && target != null) {
                if (data.targetSpeedX != 0f) {
                    this.speedX = if (target.centerHorizontal > this.centerHorizontal) data.targetSpeedX else -data.targetSpeedX
                }
                if (data.targetSpeedY != 0f) {
                    this.speedY = if (target.centerVertical > this.centerVertical) data.targetSpeedY else -data.targetSpeedY
                }
            }

            this.lifeTimeLeft -= delta
            if (this.lifeTimeLeft <= 0) {
                this.remove = true
            }
        }
    }

    fun collisionSkill(s: MSkill) {
        val healthS = s.health
        s.damage(health, this)
        this.damage(healthS, s)
    }

    fun collisionEnemy(e: ILiving, collisionType: MWCollisionType) {
        if (!this.active) {
            return
        }

        var damageFactor = 1f
        if (e is IUpgraded) {
            damageFactor *= e.getArmor(collisionType).getSkillEffectivity(data.skillClass).damageFactor

            if (damageFactor == 0f) { // If entity is immune to skill type
                knockbackSkill(e, MWSkillKnockbackReason.ARMOR)
                return
            }
        }

        if (o is IUpgraded) {
            damageFactor += o.getSkillMultiplier(data.skillClass)
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
            val knockbackY = Math.abs(Math.max(speedY, speedX / 3)) + 50
            e.knockback(knockbackX * knockbackFactor, knockbackY * Math.abs(knockbackFactor), direction)
        }

        // Damage
        val damage = data.damage * this.propertyScale
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
        val skillDamage = min(MConstants.MAXIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT, e.health)
        // MSkill damage
        if (!killed) {
            this.damage(max(skillDamage, MConstants.MINIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT_NO_KILL), e)
        } else {
            this.damage(max(skillDamage, MConstants.MINIMUM_SKILL_DAMAGE_BY_ENEMY_ON_IMPACT_KILL), e)
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

        speedX = Math.max(400f, cause.speedX) * direction
        speedY = Math.min(100f, cause.speedY)
        accelerationX = 0f
        accelerationY = MConstants.GRAVITY_ACCELERATION
        //setupMovement(s, 400, -100, 0, 90 * ACC_FACTOR)
    }


    fun damage(value: Float, damager: IEntity): Boolean {
        var i = value / propertyScale
        this.health -= i
        if (this.health <= 0) {
            remove = true
            return true
        }
        return false
    }




}