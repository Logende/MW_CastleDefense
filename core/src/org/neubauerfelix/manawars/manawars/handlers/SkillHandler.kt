package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysisPart
import org.neubauerfelix.manawars.manawars.analysis.SkillAnalysisDummy
import org.neubauerfelix.manawars.manawars.data.actions.*
import org.neubauerfelix.manawars.manawars.entities.*
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.lang.RuntimeException


class SkillHandler : ISkillAnalysisHandler, ISkillSetupHandler {

    companion object {
        const val TARGET_BOTTOM = 0
        const val TARGET_TOP = TARGET_BOTTOM + MConstants.BODY_HUMAN_HEIGHT
        const val OWNER_CENTRE_VER = MConstants.BODY_HUMAN_HEIGHT / 2

        private const val MAX_RANGE = 2000f
        private const val SIMULATION_STEP_TIME = 1f / 50f
        private const val SIMULATION_MAX_LIFE_DURATION = 10f
        private const val SIMULATION_MAX_STEPS = (SIMULATION_MAX_LIFE_DURATION / SIMULATION_STEP_TIME).toInt()
        private const val SIMULATION_BORDER_BOTTOM = GameConstants.CONTROLPANEL_HEIGHT - 700f
        private const val SIMULATION_BORDER_TOP = GameConstants.CONTROLPANEL_HEIGHT + GameConstants.BACKGROUND_HEIGHT + 700f
        private const val SIMULATION_BORDER_LEFT = -700f
        private const val SIMULATION_BORDER_RIGHT = MAX_RANGE + 700

        private const val TACTICAL_DAMAGE_FACTOR_STATEFFECT = 0.35f
    }


    private var dummyTarget: IControlled? = null // If not null, this will be the targer assigned to skills seeking for a target

    /**
     * This config file contains all prepared skill analyses. Basically when a skill is loaded, the existing analysis from this config
     * is used. If no analysis exists yet, a dummy analysis (containing placeholder values) will be created.
     *
     * That way the game can run even when analyses are missing. New analyses can be generated using the
     * #analyseSkills method, which creates a new analysis file.
     */
    private val config: Configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
            load("content/skills/${MConstants.SKILL_ANALYSIS_FILE_NAME}", true)



    override fun analyseSkills(fileName: String) {
        val config = Configuration()
        for (data in MManaWars.m.getActionHandler().listActions()) {
            if (data is DataSkillLoaded) {
                println("Analysing skill ${data.name}.")
                val section = config.getSection(data.name)
                data.analyseSkill()
                val map = data.analysis
                for ((type, analysis) in map) {
                    section.set("${type.name}.height", analysis.height)
                    section.set("${type.name}.width", analysis.width)
                    section.set("${type.name}.successProbability", analysis.successProbability)
                    section.set("${type.name}.lifeTime", analysis.lifeTime)
                    section.set("${type.name}.strategicValue", analysis.strategicValue)
                    section.set("${type.name}.offensiveStrength", analysis.offensiveStrength)
                    section.set("${type.name}.defensiveStrength", analysis.defensiveStrength)
                    for ( (type, percentage) in analysis.collisionsPercentages) {
                        section.set("${type.name}.collisionPercentage.${type.name}", percentage)
                    }
                    for ( (type, range) in analysis.rangeMax) {
                        section.set("${type.name}.rangeMax.${type.name}", range)
                    }
                    for ( (type, range) in analysis.rangeMin) {
                        section.set("${type.name}.rangeMin.${type.name}", range)
                    }
                }
            }
        }
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, fileName, false)
    }


    override fun loadSkillAnalysis(data: IDataSkill): Map<MWEntityAnimationType, ISkillAnalysis> {
        val map: MutableMap<MWEntityAnimationType, ISkillAnalysis> = hashMapOf()

        // no analysis existing? Return dummy
        if (! config.contains(data.name)) {
            print("No analysis of skill ${data.name} found. Using dummy.")
            MWEntityAnimationType.values().forEach { type ->
                map[type] = SkillAnalysisDummy()
                return map
            }
        }

        val sectionSkill = config.getSection(data.name)
        for (typeOwner in MWEntityAnimationType.values()) {
            val section = sectionSkill.getSection(typeOwner.name)
            val height = section.getInt("height")
            val width = section.getInt("width")
            val successProbability = section.getFloat("successProbability")
            val lifeTime = section.getFloat("lifeTime")
            val strategicValue = section.getFloat("strategicValue")
            val offensivePoints = section.getFloat("offensiveStrength")
            val defensivePoints = section.getFloat("defensiveStrength")
            val rangeMaxAvg = section.getFloat("rangeMaxAvg")
            val rangeMax: MutableMap<MWEntityAnimationType, Int> = HashMap()
            val rangeMin: MutableMap<MWEntityAnimationType, Int> = HashMap()
            for (typeTarget in MWEntityAnimationType.values()) {
                rangeMax[typeTarget] = section.getInt("rangeMax.${typeTarget.name}")
                rangeMin[typeTarget] = section.getInt("rangeMin.${typeTarget.name}")
            }
            val collisionsPercentages: MutableMap<MWArmorHolder, Double> = HashMap()
            for (type in MWArmorHolder.values()) {
                collisionsPercentages[type] = section.getDouble("collisionPercentage.${type.name}")
            }

            map[typeOwner] = object : ISkillAnalysis {
                override val lifeTime: Float = lifeTime
                override val width: Int = width
                override val height: Int = height
                override val strategicValue: Float = strategicValue
                override val successProbability: Float = successProbability
                override val offensiveStrength: Float = offensivePoints
                override val defensiveStrength: Float = defensivePoints
                override val collisionsPercentages: Map<MWArmorHolder, Double> = collisionsPercentages
                override val rangeMax: Map<MWEntityAnimationType, Int> = rangeMax
                override val rangeMin: Map<MWEntityAnimationType, Int> = rangeMin
                override val rangeMaxAvg: Float = rangeMaxAvg
                override val skillClass: MWSkillClass = data.skillClass
            }
        }
        return map
    }

    /**
     * Calculate:
     * - Movement Score (Range, Size, etc.): "how good is this data at successfully reaching enemies.
     *   Movement Score is calculated as for example 0.7 * humanMovementScore + 0.2 * riderMovementScore + 0.1 * airMovementScore (or even stored as separate values)
     *   Preferably fast and with long range.
     * - Attack heights (MSkill attack height body/head in percentage)
     * - Range
     * - Attack Score (Damage, Knockback, etc.): "how powerful is the data when it hits"
     * - Defense Score (Strength, Size, etc.): "how good is this data at blocking other skills"
     */
    override fun analyse(data: IDataSkill, entityAnimationType: MWEntityAnimationType): ISkillAnalysis {
        val parts = MWEntityAnimationType.values().map { type -> this.analyse(data, entityAnimationType, type) }
        var lifetime = 0f
        val rangeMax: MutableMap<MWEntityAnimationType, Int> = HashMap()
        val rangeMin: MutableMap<MWEntityAnimationType, Int> = HashMap()
        for (part in parts) {
            lifetime = Math.max(lifetime, part.lifeTime)
            rangeMax[part.targetAnimationType] = part.rangeMax
            rangeMin[part.targetAnimationType] = part.rangeMin
        }

        val tacticalDamage = parts.sumByDouble { part -> (part.tacticalDamage * part.targetAnimationType.share).toDouble() }
        val hitProbability = parts.sumByDouble { part -> (part.hitProbability * part.targetAnimationType.share).toDouble() }
        val tacticalStrength = parts.sumByDouble { part -> (part.tacticalStrength * part.targetAnimationType.share).toDouble() }

        val collisionsPercentages: MutableMap<MWArmorHolder, Double> = hashMapOf()
        MWArmorHolder.values().forEach {
            val percentage = parts.sumByDouble { part -> (part.collisionsPercentages[it]!! * part.targetAnimationType.share) }
            collisionsPercentages[it] = percentage
        }

        val rangeMaxAvg = rangeMax.map { (animationType, range) ->
            animationType.share * range.toFloat()
        }.sum()

        return object : ISkillAnalysis {
            override val rangeMax: Map<MWEntityAnimationType, Int> = rangeMax
            override val rangeMin: Map<MWEntityAnimationType, Int> = rangeMin
            override val rangeMaxAvg: Float = rangeMaxAvg
            override val lifeTime: Float = lifetime
            override val width: Int = parts[0].width
            override val height: Int = parts[0].height
            override val strategicValue: Float  = tacticalDamage.toFloat()
            override val successProbability: Float = hitProbability.toFloat()
            override val offensiveStrength: Float = tacticalStrength.toFloat() // TODO: Only give offensive points, if skill actually does attack
            override val defensiveStrength: Float = tacticalStrength.toFloat()
            override val collisionsPercentages: Map<MWArmorHolder, Double> = collisionsPercentages
            override val skillClass: MWSkillClass = data.skillClass
        }
    }



    private fun analyse(data: IDataSkill, entityAnimationType: MWEntityAnimationType, targetAnimationType: MWEntityAnimationType): ISkillAnalysisPart {
        require(data.loaded)

        // Average speed values
        var speedXAvg = 0f

        // Note: Skills which have big acceleration but slow start speed will likely have small distance in one second
        var distanceInSecond = -1f // Typical values: arrow 1000-1100, crossbow 1600, sword 200-700, shuriken 700, axe 120 - 800

        // Collision body part share. Combined from all animationtypes, each animationtype having a different weight.
        var collisionsPercentageHumanHead = 0.0
        var collisionsPercentageHumanBody = 0.0
        var collisionsPercentageMount = 0.0
        var rangeMax = 0f
        var distanceMax = 0f // difference to rangeMax: No collision needed for distanceMax but collision needed for rangeMax
        var rangeMin = Float.MAX_VALUE
        var lifeTime = 0f

        val owner = entityAnimationType.createDummy(0f, GameConstants.CONTROLPANEL_HEIGHT, data) // owner stands on left of screen (x = 0) looking towards right
        val target = targetAnimationType.createDummy(0f, GameConstants.CONTROLPANEL_HEIGHT, data) // The target is moved with the data (target left is always on data centre); Exception: Targeting skills
        val targetFarAway = targetAnimationType.createDummy(3000f, GameConstants.CONTROLPANEL_HEIGHT, data) // Far away. Used for targeting skills, which for example aim at the y pos of this target.

        // If has target speed: Assign dummy target to test range of skill
        if (data.targetSpeedX != 0f || data.targetSpeedY != 0f) {
            dummyTarget = targetFarAway
        }

        val skill = MSkill(data, owner)
        dummyTarget = null

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
                        || skill.bottom < SIMULATION_BORDER_BOTTOM || skill.top > SIMULATION_BORDER_TOP
                        || skill.left < SIMULATION_BORDER_LEFT || skill.right > SIMULATION_BORDER_RIGHT
                        || rangeMax >= MAX_RANGE) {
                    lifeTime = i * SIMULATION_STEP_TIME
                    speedXAvg /= i
                    break // reached border
                }

                // Detect collision type and calculate range.
                if (skill.active) {
                    val collisionType = (target as IAnimatedLiving).animation.getCollisionType(skill)
                    when (collisionType) {
                        MWCollisionType.HUMAN_ARM, MWCollisionType.HUMAN_BODY, MWCollisionType.HUMAN_FOOT -> {
                            collisionsPercentageHumanBody += 1.0
                        }

                        MWCollisionType.HUMAN_HEAD -> {
                            collisionsPercentageHumanHead += 1.0
                        }

                        MWCollisionType.MOUNT_FOOT, MWCollisionType.MOUNT_BODY, MWCollisionType.MOUNT_HEAD -> {
                            collisionsPercentageMount += 1.0
                        }
                    }
                    if (collisionType != MWCollisionType.NONE) {
                        break
                    }
                }
                skill.doLogic(SIMULATION_STEP_TIME)
                skill.move(SIMULATION_STEP_TIME)
                speedXAvg += skill.speedX
                i++
            }
            rangeMin = 0f
            rangeMax = data.targetRange
            distanceMax = data.targetRange

        } else {
            var collisionsHumanHead = 0
            var collisionsHumanBody = 0
            var collisionsMount = 0
            var collisionsOther = 0
            var collisionsNone = 0
            var startDistanceTimer = if (data.startSpeedX > 0f) 0 else -1 // iteration step index on which skill starts moving to right


            var i = 0
            while (true) { // Simulate skill movement
                if (i >= SIMULATION_MAX_STEPS
                        || skill.bottom < SIMULATION_BORDER_BOTTOM || skill.top > SIMULATION_BORDER_TOP
                        || skill.left < SIMULATION_BORDER_LEFT || skill.right > SIMULATION_BORDER_RIGHT
                        || rangeMax >= MAX_RANGE) {
                    lifeTime = i * SIMULATION_STEP_TIME
                    speedXAvg /= i
                    break // reached border
                }

                target.centerHorizontal = skill.centerHorizontal
                target.doLogic(0f) // updates location
                // Detect collision type and calculate range.
                val collisionType = (target as IAnimatedLiving).animation.getCollisionType(skill)
                when (collisionType) {
                    MWCollisionType.HUMAN_ARM, MWCollisionType.HUMAN_BODY, MWCollisionType.HUMAN_FOOT -> collisionsHumanBody += 1
                    MWCollisionType.HUMAN_HEAD -> collisionsHumanHead += 1
                    MWCollisionType.MOUNT_FOOT, MWCollisionType.MOUNT_BODY, MWCollisionType.MOUNT_HEAD -> collisionsMount += 1
                    MWCollisionType.NONE -> collisionsNone += 1
                    else -> collisionsOther += 1
                }
                target.left = skill.centerHorizontal
                target.doLogic(0f) // updates location
                distanceMax = Math.max(distanceMax, owner.getDistanceHor(target))
                if (collisionType != MWCollisionType.NONE) {
                    rangeMax = Math.max(rangeMax, owner.getDistanceHor(target))
                    target.right = skill.centerHorizontal
                    target.doLogic(0f) // updates location
                    rangeMin = Math.max(0f, Math.min(rangeMin, owner.getDistanceHor(target)))
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

            val collisionsSum = collisionsHumanHead + collisionsHumanBody + collisionsMount + collisionsOther
            collisionsPercentageHumanBody += collisionsHumanBody.toDouble() / collisionsSum.toDouble()
            collisionsPercentageHumanHead += collisionsHumanHead.toDouble() / collisionsSum.toDouble()
            collisionsPercentageMount += collisionsMount.toDouble() / collisionsSum.toDouble()
        }

        val collisionsPercentages: Map<MWArmorHolder, Double> = hashMapOf(Pair(MWArmorHolder.HUMAN_BODY, collisionsPercentageHumanBody),
                Pair(MWArmorHolder.HUMAN_HEAD, collisionsPercentageHumanHead), Pair(MWArmorHolder.MOUNT, collisionsPercentageMount))



        val hitProbability: Float = if (data.xRelativeToTarget) {
            Math.max(1f, (skill.width / target.width)) * 0.9f // if skill size is as big as multiple entities: increase hit probability
        } else {
            val rangeFactor = 0.41f + rangeMax / 4000.0f // linear; results in 0.8 for 400 range and 0.9 for 2000 range
            val speedFactor = 0.784f + distanceInSecond / 3817.0f // linear; results in 0.8 for 100 and 1.2 for 1600
            val sizeFactor = 1.0f // TODO ?
            val strengthFactor = 0.47f + data.skillStrength / 22.0f
            rangeFactor * sizeFactor * speedFactor * strengthFactor
        }

        // Attack Score
        val tacticalDamageDamage = data.damageMin + (data.damageMax - data.damageMin) / 2f
        val damageSkillClassFactor = MWArmorType.values().map { t ->
            t.share * t.getSkillEffectivity(data.skillClass).damageFactor
        }.sum()


        val tacticalDamageEffect: Float = if (data.stateEffect == null) {
            0f
        } else {
            data.stateEffect!!.tacticalDamage * data.stateEffectDuration
        }


        val tacticalDamage = tacticalDamageDamage * damageSkillClassFactor + tacticalDamageEffect * TACTICAL_DAMAGE_FACTOR_STATEFFECT
        // + speedXHitAvg * data.knockbackFactor / 20.0


        //System.out.println("entity $entityAnimationType target $targetAnimationType skill ${data.name}  hit probability $successProbability with tactical damage $strategicValue")
        // TODO: If has "spawn_on_impact" then consider the properties of the connected action

        return object : ISkillAnalysisPart {
            override val tacticalDamage: Float = tacticalDamage
            override val hitProbability: Float = hitProbability
            override val tacticalStrength: Float = data.skillStrength.toFloat()
            override val collisionsPercentages: Map<MWArmorHolder, Double> = collisionsPercentages
            override val rangeMax = rangeMax.toInt()
            override val rangeMin = rangeMin.toInt()
            override val lifeTime: Float = lifeTime
            override val width: Int = skill.width.toInt()
            override val height: Int = skill.height.toInt()
            override val attackerAnimationType: MWEntityAnimationType = entityAnimationType
            override val targetAnimationType: MWEntityAnimationType = targetAnimationType
        }
    }

    override fun findTarget(data: IDataSkill, owner: IActionUser): IEntity? {
        if (data.targetEnemy) {
            if (dummyTarget != null) {
                return dummyTarget
            }
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
        if (data.xRelativeToTarget && target != null) {
            skill.centerHorizontal = target.centerHorizontal + data.xOffset * owner.direction * skill.propertyScale
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

        if (data.targetEnemy && data.allowMovementScaling && target != null && target is IAnimated) {
            val rangeMin = data.getActionProperties(owner.entityAnimationType).rangeMin.getValue(target.entityAnimationType)
            val rangeMax = data.getActionProperties(owner.entityAnimationType).rangeMax.getValue(target.entityAnimationType)
            val defaultRange = rangeMin + (rangeMax - rangeMin) / 2
            val distanceTarget = Math.abs(skill.centerHorizontal - target!!.centerHorizontal)
            val scale = distanceTarget / defaultRange.toFloat()
            skill.speedX *= scale
            skill.speedY *= scale
            skill.accelerationX *= scale
            skill.accelerationY *= scale
        }
    }
}