package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.*
import org.neubauerfelix.manawars.manawars.data.actions.*
import org.neubauerfelix.manawars.manawars.entities.*
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.lang.RuntimeException
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


class SkillSetupHandler : ISkillSetupHandler {

    private var dummyTarget: IControlled? = null // If not null, this will be the targer assigned to skills seeking for a target

    override fun setDummyTarget(target: IControlled?) {
        this.dummyTarget = target
    }

    override fun findTarget(data: IDataSkill, owner: IActionUser): IEntity? {
        if (data.model.targetEnemy) {
            if (dummyTarget != null) {
                return dummyTarget
            }
            val entities = AManaWars.m.screen.getEntities{e ->
                // only pick enemies in the direction the owner is looking to
                ! ITeamable.isTeamed(owner,e) && e is ILiving
                        && (e.centerHorizontal - owner.centerHorizontal) * owner.direction > 0f
                        && e.getDistanceHor(owner) < data.model.targetRange * owner.propertyScale
            }
            if (entities.isNotEmpty()) {
                return entities.sortedBy { e -> e.getDistanceHor(owner) }.first()
            }
        }
        return null
    }

    override fun setupLocation(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?) {
        // X
        if (data.model.xRelativeToTarget && target != null) {
            skill.centerHorizontal = target.centerHorizontal + data.model.xOffset * owner.direction * skill.propertyScale
        } else {
            if (owner.direction == 1) {
                skill.x = owner.right - skill.width/2 + data.model.xOffset * skill.propertyScale
            } else {
                skill.x = owner.left - skill.width/2 - data.model.xOffset * skill.propertyScale
            }
        }

        // Y
        if (data.model.yRelativeToTarget && target != null) {
            skill.centerVertical = target.centerVertical + data.model.yOffset * skill.propertyScale
        } else if (data.model.yRelativeToGround) {
            skill.top = GameConstants.WORLD_HEIGHT + data.model.yOffset * skill.propertyScale
        } else {
            skill.centerVertical = owner.centerVertical + data.model.yOffset * skill.propertyScale
        }
    }

    override fun setupMovement(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?) {
        skill.accelerationX = data.model.accelerationX * owner.direction
        skill.accelerationY = data.model.accelerationY
        skill.speedX = data.model.startSpeedX * owner.direction
        skill.speedY = data.model.startSpeedY

        if (data.targetEnemy && data.allowMovementScaling && target != null && target is IAnimated) {
            val distance = skill.getDistanceHor(target)
            val speedFactor = sqrt(- (distance * skill.accelerationY) / ( 2* skill.speedX * skill.speedY))
            skill.speedX *= speedFactor
            skill.speedY *= speedFactor
        }
    }
}