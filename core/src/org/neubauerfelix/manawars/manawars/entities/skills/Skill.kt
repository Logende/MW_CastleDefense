package org.neubauerfelix.manawars.manawars.entities.skills

import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimationSimple
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars


class Skill(val data: IDataSkill, val owner: IActionUser): MEntityAnimationSimple(data.animation!!, data.textureScale, data.color) {

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

    init {
        // Set up basic values
        health = data.skillStrength
        direction = owner.direction * (if (data.startSpeedX > 0) 1 else -1)
        idleTimeLeft = data.idleTime
        lifeTimeLeft = data.lifeTime

        target = MManaWars.m.getSkillSetupHandler().findTarget(this, data, owner)
        MManaWars.m.getSkillSetupHandler().setupLocation(this, data, owner, target)
        if (! this.idle) {
            MManaWars.m.getSkillSetupHandler().setupMovement(this, data, owner, target)
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
                MManaWars.m.getSkillSetupHandler().setupMovement(this, data, owner, target)
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


    // TODO: Handle collisions and damage enemies when hitting


}