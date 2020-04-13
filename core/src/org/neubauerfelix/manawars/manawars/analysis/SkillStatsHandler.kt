package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.*
import org.neubauerfelix.manawars.manawars.data.units.IDataActionUser
import org.neubauerfelix.manawars.manawars.entities.*
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.lang.RuntimeException
import kotlin.math.max
import kotlin.math.min


/*
WARNING: This file does NOT generate instantly usable skill stats every run. Instead it creates
skill stats that are in a file "skillanalysis.yml". That file has to be manually copied to the skills folder.
Then the game will be able to use the stats in following game launches.
 */

class SkillStatsHandler : ISkillStatsHandler {

    companion object {
        private const val SIMULATION_STEP_TIME = 1f / 50f
        private const val SIMULATION_MAX_LIFE_DURATION = 10f
        private const val SIMULATION_MAX_STEPS = (SIMULATION_MAX_LIFE_DURATION / SIMULATION_STEP_TIME).toInt()
        private const val SIMULATION_BORDER_BOTTOM = GameConstants.WORLD_HEIGHT_UNITS + 700f
        private const val SIMULATION_BORDER_TOP = - 1000f
        private const val SIMULATION_BORDER_LEFT = - 700f
        private const val SIMULATION_BORDER_RIGHT = 2500f

        private const val TACTICAL_DAMAGE_FACTOR_STATEFFECT = 0.35f
        private const val TACTICAL_STRENGTH_FACTOR_VERTICAL_SKILL = 0.4f
    }


    /**
     * This config file contains all prepared skill analyses. Basically, when a skill is loaded, the existing analysis from this config
     * is used. If no analysis exists yet, a dummy analysis (containing placeholder values) will be created.
     *
     * That way the game can run even when analyses are missing. New analyses can be generated using the
     * #generateStats(fileName, skills) method, which creates a new analysis file.
     */
    private val config: Configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
            load("content/skills/${MConstants.SKILL_STATS_FILE}", true)


    override fun analyseSkills(actionUsers: Collection<IDataActionUser>) {
        val skillMap = HashMap<IDataSkill, Float>()
        actionUsers.forEach {
            this.considerAction(it.action, it.unitType.defaultRange, skillMap)
        }
        this.generateStats(MConstants.SKILL_STATS_FILE, skillMap)
    }

    private fun considerAction(action: IDataAction, maxPossibleRange: Float, skillMap: MutableMap<IDataSkill, Float>) {
        if (action is IDataSkill) {
            skillMap[action] = maxPossibleRange
        } else if (action is DataSkillMixLoaded) {
            action.parts.forEach {
                this.considerAction(it.action, maxPossibleRange, skillMap)
            }
        }
    }


    override fun readStats(data: IDataSkill): ISkillStats {
        val stats = readStatsFromConfig(data, config)
        return stats?:generateDefaultStats()
    }

    private fun generateDefaultStats() : ISkillStats {
        println("Generating default stats for skill.")
        return object : ISkillStats {
            override val lifeTime: Float = 1f
            override val rangeMax = Float.MAX_VALUE
            override val rangeMin = 0f
        }
    }
    private fun readStatsFromConfig(data: IDataSkill, config: Configuration) : ISkillStats? {
        return if (config.contains(data.name)) {
            val section = config.getSection(data.name)
            val lifeTime = section.getFloat("lifeTime")
            val rangeMax = section.getFloat("rangeMax")
            val rangeMin = section.getFloat("rangeMin")
            object : ISkillStats {
                override val lifeTime: Float = lifeTime
                override val rangeMax = rangeMax
                override val rangeMin = rangeMin
            }
        } else {
            null
        }
    }


    override fun generateStats(fileName: String, skills: Map<IDataSkill, Float>) {
        val config = Configuration()
        for ((data, maxPossibleRange) in skills) {
            generateStats(data, config, maxPossibleRange)
        }
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, fileName, false)
    }

    private fun generateStats(data: IDataSkill, config: Configuration, maxPossibleRange: Float) : ISkillStats {
        println("Analysing skill ${data.name}.")
        val stats = this.generateStats(data, MWEntityAnimationType.HUMAN, MWEntityAnimationType.HUMAN, maxPossibleRange)
        this.writeStatsToConfig(stats, data.name, config)
        return stats
    }

    private fun writeStatsToConfig(stats: ISkillStats, skillName: String, config: Configuration) {
        val section = config.getSection(skillName)
        section.set("lifeTime", stats.lifeTime)
        section.set("rangeMax", stats.rangeMax)
        section.set("rangeMin", stats.rangeMin)
    }

    private fun generateStats(data: IDataSkill, entityAnimationType: MWEntityAnimationType,
                              targetAnimationType: MWEntityAnimationType, maxPossibleRange: Float): ISkillStats {
        // Average speed values
        var speedXAvg = 0f

        // Note: Skills which have big acceleration but slow start speed will likely have small distance in one second
        var distanceInSecond = -1f // Typical values: arrow 1000-1100, crossbow 1600, sword 200-700, shuriken 700, axe 120 - 800

        var collided = false
        var rangeMax = 0f
        var distanceMax = 0f // difference to rangeMax: No collision needed for distanceMax but collision needed for rangeMax
        var rangeMin = Float.MAX_VALUE
        var lifeTime = 0f

        val owner = entityAnimationType.createDummy(0f, GameConstants.WORLD_HEIGHT_UNITS, data, 1) // owner stands on left of screen (x = 0) looking towards right
        val target = targetAnimationType.createDummy(0f, GameConstants.WORLD_HEIGHT_UNITS, data, -1) // The target is moved with the data (target left is always on data centre); Exception: Targeting skills
        val targetFarAway = targetAnimationType.createDummy(3000f, GameConstants.WORLD_HEIGHT_UNITS, data, -1) // Far away. Used for targeting skills, which for example aim at the y pos of this target.


        // If has target speed: Assign dummy target to test range of skill
        if (data.targetSpeedX != 0f || data.targetSpeedY != 0f) {
            MManaWars.m.getSkillSetupHandler().setDummyTarget(targetFarAway)
        }

        assert(data.animation != null)
        val skill = MSkill(data, owner)
        MManaWars.m.getSkillSetupHandler().setDummyTarget(null)

        if (data.yRelativeToTarget) {
            throw RuntimeException("MSkill property yRelativeToTarget not supported yet.")
        }


        if (data.xRelativeToTarget) {
            target.x = 800f
            target.doLogic(0f)
            if (data.targetSpeedX != 0f || data.targetSpeedY != 0f) {
                throw RuntimeException("MSkill property xRelativeToTarget combined with target speed is not supported yet.")
            }

            skill.centerHorizontal = target.centerHorizontal + data.xOffset * owner.direction * skill.propertyScale

            var i = 0
            while (true) { // Simulate skill movement
                if (i >= SIMULATION_MAX_STEPS
                        || skill.bottom > SIMULATION_BORDER_BOTTOM || skill.top < SIMULATION_BORDER_TOP
                        || skill.left < SIMULATION_BORDER_LEFT || skill.right > SIMULATION_BORDER_RIGHT) {
                    lifeTime = i * SIMULATION_STEP_TIME
                    speedXAvg /= i
                    break // reached border
                }

                // Detect collision type and calculate range.
                if (skill.active) {
                    if (! collided) { // only count first collision here
                        val collisionType = (target as IAnimatedLiving).animation.getCollisionType(skill)
                        collided = collisionType != MWCollisionType.NONE
                    }
                    i++
                }
                skill.doLogic(SIMULATION_STEP_TIME)
                skill.move(SIMULATION_STEP_TIME)
                speedXAvg += skill.speedX
            }
            rangeMin = 0f
            rangeMax = data.targetRange
            distanceMax = data.targetRange

        } else {
            var startDistanceTimer = if (data.startSpeedX > 0f) 0 else -1 // iteration step index on which skill starts moving to right
            val isJumpSkill = this.isJumpSkill(data)

            var i = 0
            while (true) { // Simulate skill movement
                if (i >= SIMULATION_MAX_STEPS
                        || skill.bottom > SIMULATION_BORDER_BOTTOM || skill.top < SIMULATION_BORDER_TOP
                        || skill.left < SIMULATION_BORDER_LEFT || skill.right > SIMULATION_BORDER_RIGHT
                        || rangeMax >= maxPossibleRange) {
                    lifeTime = i * SIMULATION_STEP_TIME
                    speedXAvg /= i
                    break // reached border
                }

                target.centerHorizontal = skill.right
                target.doLogic(0f) // updates location
                // Detect collision type and calculate range.
                val collisionType = (target as IAnimatedLiving).animation.getCollisionType(skill)
                target.left = skill.centerHorizontal
                target.doLogic(0f) // updates location
                distanceMax = max(distanceMax, owner.getDistanceHor(target))
                // count collision if it is detected and in case of jumping skills: only when already falling down
                if (collisionType != MWCollisionType.NONE && (!isJumpSkill || skill.speedY >= 0)) {
                    rangeMax = max(rangeMax, owner.getDistanceHor(target))
                    target.right = skill.centerHorizontal
                    target.doLogic(0f) // updates location
                    rangeMin = max(0f, min(rangeMin, owner.getDistanceHor(target)))
                }
                skill.doLogic(SIMULATION_STEP_TIME)
                skill.move(SIMULATION_STEP_TIME)
                speedXAvg += skill.speedX
                i++
                if (startDistanceTimer == -1 && skill.speedX > 0f) { // if timer is not enabled yet but skill is moving to right: enable it
                    startDistanceTimer = i
                }
                if (distanceInSecond == -1f && (i - startDistanceTimer) * SIMULATION_STEP_TIME >= 1f) {
                    target.left = skill.centerHorizontal
                    target.doLogic(0f) // updates location
                    distanceInSecond = distanceMax
                }
            }

        }


        if (data.skillClass == MWSkillClass.SHIELD) {
            rangeMin = 0f
            rangeMax = 500f
        }
        if (data.targetRange > 0) {
            rangeMax = min(rangeMax, data.targetRange)
        }

        if (data.allowMovementScaling) {
            lifeTime *= 4
        }

        // TODO: think about finding one common range for every animation type.

        return object : ISkillStats {
            override val rangeMax = rangeMax
            override val rangeMin = rangeMin
            override val lifeTime: Float = lifeTime
        }
    }

    fun isJumpSkill(data: IDataSkill) : Boolean {
        return data.startSpeedX > 100f
                && data.startSpeedY < -500f
                && data.accelerationY > 5*DataSkillLoaded.ACC_FACTOR
                &&! data.stopOnGround
    }

}