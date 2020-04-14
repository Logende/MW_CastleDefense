package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.data.actions.*
import org.neubauerfelix.manawars.manawars.entities.*
import kotlin.math.abs
import kotlin.math.sqrt


class SkillSetupHandler : ISkillSetupHandler {

    private var dummyTarget: IControlled? = null // If not null, this will be the targer assigned to skills seeking for a target

    override fun setDummyTarget(target: IControlled?) {
        this.dummyTarget = target
    }

    override fun findTarget(data: IDataSkill, owner: IActionUser): IEntity? {
        if (data.targetEnemy) {
            if (dummyTarget != null) {
                return dummyTarget
            }
            val entities = AManaWars.m.screen.getEntities{e ->
                // only pick enemies in the direction the owner is looking to
                ! ITeamable.isTeamed(owner,e) && e is ILiving
                        && (e.centerHorizontal - owner.centerHorizontal) * owner.direction > 0f
                        && e.getDistanceHor(owner) < data.targetRange * owner.propertyScale
            }
            if (entities.isNotEmpty()) {
                return entities.sortedBy { e -> e.getDistanceHor(owner) }.first()
            }
        }
        return null
    }

    override fun setupLocation(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?) {
        // X
        if (data.xRelativeToTarget && target != null) {
            val closestTargetX = if (owner.direction == 1) target.left else target.right
            skill.centerHorizontal = closestTargetX + data.xOffset * owner.direction * skill.propertyScale
        } else {
            if (owner.direction == 1) {
                skill.x = owner.right - skill.width/2 + data.xOffset * skill.propertyScale
            } else {
                skill.x = owner.left - skill.width/2 - data.xOffset * skill.propertyScale
            }
        }

        // Y
        if (data.yRelativeToTarget && target != null) {
            skill.centerVertical = target.centerVertical + data.yOffset * skill.propertyScale
        } else if (data.yRelativeToGround) {
            skill.top = GameConstants.WORLD_HEIGHT_UNITS + data.yOffset * skill.propertyScale
        } else {
            skill.centerVertical = owner.centerVertical + data.yOffset * skill.propertyScale
        }
    }

    override fun setupMovement(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?) {
        skill.accelerationX = data.accelerationX * owner.direction
        skill.accelerationY = data.accelerationY
        skill.speedX = data.startSpeedX * owner.direction
        skill.speedY = data.startSpeedY
        if (data.targetEnemy && data.allowMovementScaling && target != null && target is IAnimated) {
            val distance = skill.getDistanceHor(target)
            val speedFactor = sqrt(- (distance * skill.accelerationY) / ( 2* abs(skill.speedX) * skill.speedY))
            skill.speedX *= speedFactor
            skill.speedY *= speedFactor
        }
    }
}