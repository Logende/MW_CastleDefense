package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.actions.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.ITeamable


class SkillHandler : ISkillAnalysisHandler, ISkillSetupHandler {


    companion object {
        const val TARGET_BOTTOM = 0
        const val TARGET_TOP = TARGET_BOTTOM + MConstants.BODY_HUMAN_HEIGHT
        const val OWNER_CENTRE_VER = MConstants.BODY_HUMAN_HEIGHT / 2
    }

    override fun analyse(skill: IDataSkill): ISkillAnalysis {
        // TODO
        return object : ISkillAnalysis{
            override val rangeMax: Int = 1000
            override val rangeMin: Int = 0
            override val manaCost: Int = 0
            override val lifeTime: Float = 100000f
            override val width: Int = 200
            override val height: Int = 200
        }
    }

    override fun findTarget(data: IDataSkill, owner: IActionUser): IEntity?{
        if (data.targetEnemy) {
            val entities = AManaWars.m.screen.getEntities{e ->
                // only pick enemies in the direction the owner is looking to
                ! ITeamable.isTeamed(owner,e)
                        && (e.centerHorizontal - owner.centerHorizontal) * owner.direction > 0f
                        && e.getDistanceHor(owner) < data.targetRange * owner.propertyScale
            }
            if (!entities.isEmpty()) {
                return entities.sortedByDescending { e -> e.getDistanceHor(owner) }.first()
            }
        }
        return null
    }

    override fun setupLocation(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?) {
        // X
        if (data.targetEnemy && data.xRelativeToTarget) {
            skill.centerHorizontal = target!!.centerHorizontal + data.xOffset * owner.direction * skill.propertyScale
        } else {
            if (owner.direction == 1) {
                skill.x = owner.right - skill.width/2 + data.xOffset * skill.propertyScale
            } else {
                skill.x = owner.left - skill.width/2 - data.xOffset * skill.propertyScale
            }
        }

        // Y
        if (data.targetEnemy && data.yRelativeToTarget) {
            skill.centerVertical = target!!.centerVertical + data.yOffset * skill.propertyScale
        } else if (data.yRelativeToGround) {
            skill.top = GameConstants.CONTROLPANEL_HEIGHT + data.yOffset * skill.propertyScale
        } else {
            skill.centerVertical = owner.centerVertical + data.yOffset * skill.propertyScale
        }
    }

    override fun setupMovement(skill: IMovable, data: IDataSkill, owner: IActionUser, target: IEntity?) {
        skill.accelerationX = data.accelerationX * owner.direction
        skill.accelerationY = data.accelerationY
        skill.speedX = data.startSpeedX * owner.direction
        skill.speedY = data.startSpeedY

        if (data.targetEnemy && data.allowMovementScaling) {
            val defaultRange = data.rangeMin + (data.rangeMax - data.rangeMin) / 2
            val distanceTarget = Math.abs(skill.centerHorizontal - target!!.centerHorizontal)
            val scale = distanceTarget / defaultRange.toFloat()
            skill.speedX *= scale
            skill.speedY *= scale
            skill.accelerationX *= scale
            skill.accelerationY *= scale
        }
    }
}