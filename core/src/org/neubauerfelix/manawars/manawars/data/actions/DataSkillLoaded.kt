package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.storage.Configuration


open class DataSkillLoaded(override val name: String, config: Configuration) : DataSkill() {

    companion object {
        const val ACC_FACTOR = 16f
    }

    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    /**
     * Appearance: Animation, sound, etc.
     */
    override val texturePath: String = config.getString("texture")
    override val previewTexturePath: String = if (config.contains("preview")) { config.getString("preview") } else { texturePath }

    override val animationFrequency: Float
    override val textureColumns: Int
    override val textureRows: Int
    init {
        val animation = config.getString("animation").split(":")
        animationFrequency = animation[0].toFloat()
        textureColumns = animation[1].toInt()
        textureRows = animation[2].toInt()
    }

    override val pickOneFrame: Boolean = config.getBoolean("pick_one_frame")
    override val animationRotationDuration: Float = config.getFloat("rotation_duration", if (textureColumns * textureRows > 1) 0f else 1.35f)
    override val color: Color? =
            if (config.contains("color")) {
                Colors.get(config.getString("color"))
            } else {
                null
            }

    override val soundPath: String = config.getString("sound")
    override val textureScale: Float = config.getFloat("scale", 1f)

    override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation")) { MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation")) } else { null }
    override val weaponType: MWWeaponType?
    init {
        if (config.contains("weapon") && animationEffect == MWAnimationTypeBodyEffect.WEAPON) {
            val weaponParts = config.getString("weapon").split(":")
            val weaponClass = MWWeaponClass.valueOf(weaponParts[0].toUpperCase())
            val textureName = weaponParts[1].replace("_", ".")
            weaponType = MWWeaponType(weaponClass, textureName)
        } else {
            weaponType = null
        }
    }



    /**
     * Movement: speed, location offset, targets, etc.
     */
    override val idleTime: Float = config.getFloat("idle_time") // in seconds
    override val yRelativeToGround: Boolean = config.getBoolean("y_relative_to_ground")
    override val xOffset: Float
    override val yOffset: Float
    override val startSpeedX: Float
    override val startSpeedY: Float
    override val accelerationX: Float
    override val accelerationY: Float
    init {
        val offset = config.getString("location").split(":")
        xOffset = if (!offset[0].isEmpty()) { offset[0].toFloat() } else { 0f }
        yOffset = if (offset.size >= 2 &&! offset[1].isEmpty()) { offset[1].toFloat() } else { 0f }

        val speed = config.getString("speed").split(":")
        startSpeedX = speed[0].toFloat()
        startSpeedY = speed[1].toFloat()
        // TODO: support for more calculations

        val acceleration = config.getString("acceleration")
                .replace("A", ACC_FACTOR.toString())
                .replace("G", MConstants.GRAVITY_ACCELERATION.toString())
                .split(":")
        accelerationX = MathUtils.calc(acceleration[0]).toFloat()
        accelerationY = MathUtils.calc(acceleration[1]).toFloat()
    }

    override val stopOnGround: Boolean = config.getBoolean("stop_on_ground")
    override val allowMovementScaling: Boolean = config.getBoolean("allow_movement_scaling")
    override val fixManaCost: Int = config.getInt("manacost", -1)
    override val targetEnemy: Boolean = config.contains("target_range") || config.contains("target_speed") || config.contains("adaptive_speed_x")
    override val targetRange: Float = config.getFloat("target_range", 900f)
    override val xRelativeToTarget: Boolean
    override val yRelativeToTarget: Boolean
    override val targetSpeedX: Float
    override val targetSpeedY: Float
    init {
        if (config.contains("spawn_relative_to_target")) {
            val spawnRelativeToTarget = config.getString("spawn_relative_to_target").split(":")
            xRelativeToTarget = spawnRelativeToTarget[0].toBoolean()
            yRelativeToTarget = spawnRelativeToTarget[1].toBoolean()
        } else {
            xRelativeToTarget = false
            yRelativeToTarget = false
        }
        if (config.contains("target_speed")) {
            val targetSpeed = config.getString("target_speed").split(":")
            targetSpeedX = targetSpeed[0].toFloat()
            targetSpeedY = targetSpeed[1].toFloat()
        } else {
            targetSpeedX = 0f
            targetSpeedY = 0f
        }
    }

    override val skillLimit: Int = config.getInt("limit", -1)




    /**
     * Effect: Knockback, damage, state effect, etc.
     */
    override val damage: Int =  config.getInt("damage")
    override val stateEffect: MWState?
    override val stateEffectDuration: Float
    init {
        if (!config.getString("effect").isEmpty()) {
            val effect = config.getString("effect").split(":")
            stateEffect = MWState.valueOf(effect[0])
            stateEffectDuration = effect[1].toFloat()
        } else {
            stateEffect = null
            stateEffectDuration = 0f
        }
    }
    override val knockbackFactor: Float = config.getFloat("knockback", 1f)
    override val spawnOnImpact: String? = if (config.contains("spawn_on_impact")) { config.getString("spawn_on_impact") } else { null }




    /**
     * Classification: Skillclass, range and other properties
     */
    override val skillClass: MWSkillClass = MWSkillClass.valueOf(config.getString("skillclass"))




    var analysis: Map<MWEntityAnimationType, ISkillAnalysis> = MManaWars.m.getSkillAnalysisHandler().loadSkillAnalysis(this)
        private set


    fun analyseSkill() {
        this.loadAsset()
        do {
            // wait
        } while (!AManaWars.m.getAssetLoader().areAssetsLoaded())
        this.loadedAsset()
        val analysis: MutableMap<MWEntityAnimationType, ISkillAnalysis> = hashMapOf()
        MWEntityAnimationType.values().forEach { type ->
            analysis[type] = MManaWars.m.getSkillAnalysisHandler().analyse(this, type)
        }
        this.analysis = analysis
        this.disposeAsset()
    }



    final override val lifeTime: Float
        get() =  analysis.getValue(MWEntityAnimationType.HUMAN).lifeTime


    override fun getActionProperties(entityAnimationType: MWEntityAnimationType) : IDataActionProperties {
        return analysis.getValue(entityAnimationType)
    }


}