package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.storage.Configuration


open class DataSkillLoaded(final override val name: String, config: Configuration) : DataSkill() {

    companion object {
        const val ACC_FACTOR = 16f
    }

    final override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    /**
     * Appearance: Animation, sound, etc.
     */
    final override val texturePath: String = config.getString("texture")
    final override val previewTexturePath: String = if (config.contains("preview")) {
        config.getString("preview") } else { texturePath }

    final override val animationFrequency: Float
    final override val textureColumns: Int
    final override val textureRows: Int
    init {
        val animation = config.getString("animation").split(":")
        animationFrequency = animation[0].toFloat()
        textureColumns = animation[1].toInt()
        textureRows = animation[2].toInt()
    }

    final override val pickOneFrame: Boolean = config.getBoolean("pick_one_frame")
    final override val animationRotationDuration: Float =
            config.getFloat("rotation_duration", if (textureColumns * textureRows > 1) 0f else 1.35f)
    final override val color: Color? =
            if (config.contains("color")) {
                Colors.get(config.getString("color"))
            } else {
                null
            }

    final override val soundPath: String? = config.getString("sound")
    final override val textureScale: Float = config.getFloat("scale", 1f)

    final override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation"))
    { MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation")) } else { null }
    final override val weaponType: MWWeaponType?
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
    final override val idleTime: Float = config.getFloat("idle_time") // in seconds
    final override val yRelativeToGround: Boolean = config.getBoolean("y_relative_to_ground")
    final override val xOffset: Float
    final override val yOffset: Float
    final override val startSpeedX: Float
    final override val startSpeedY: Float
    final override val accelerationX: Float
    final override val accelerationY: Float
    init {
        val offset = config.getString("location").split(":")
        xOffset = if (offset[0].isNotEmpty()) { offset[0].toFloat() } else { 0f }
        yOffset = if (offset.size >= 2 && offset[1].isNotEmpty()) { offset[1].toFloat() } else { 0f }

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

    final override val stopOnGround: Boolean = config.getBoolean("stop_on_ground")
    final override val allowMovementScaling: Boolean = config.getBoolean("allow_movement_scaling")
    final override val targetEnemy: Boolean = config.contains("target_range") || config.contains("target_speed") ||
            config.contains("adaptive_speed_x")
    final override val targetRange: Float = config.getFloat("target_range", 900f)
    final override val xRelativeToTarget: Boolean
    final override val yRelativeToTarget: Boolean
    final override val targetSpeedX: Float
    final override val targetSpeedY: Float
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

    final override val skillLimit: Int = config.getInt("limit", -1)




    /**
     * Effect: Knockback, damage, state effect, etc.
     */
    final override var damage: Int =  config.getInt("damage")
    final override val stateEffect: MWState?
    final override val stateEffectDuration: Float
    init {
        if (config.contains("effect")) {
            val effect = config.getString("effect").split(":")
            stateEffect = MWState.valueOf(effect[0])
            stateEffectDuration = effect[1].toFloat()
        } else {
            stateEffect = null
            stateEffectDuration = 0f
        }
    }
    final override val knockbackFactor: Float = config.getFloat("knockback", 1f)
    final override val spawnOnImpact: String? = if (config.contains("spawn_on_impact")) { config.getString("spawn_on_impact") } else { null }




    /**
     * Classification: Skillclass, range and other properties
     */
    override val skillClass: MWSkillClass = MWSkillClass.valueOf(config.getString("skillclass"))




    var analysis: Map<MWEntityAnimationType, ISkillAnalysis> = MManaWars.m.getSkillAnalysisHandler().loadSkillAnalysis(this)
        private set


    fun analyseSkill() {
        val analysis: MutableMap<MWEntityAnimationType, ISkillAnalysis> = hashMapOf()
        MWEntityAnimationType.values().forEach { type ->
            analysis[type] = MManaWars.m.getSkillAnalysisHandler().analyse(this, type)
        }
        this.analysis = analysis
    }



    final override val lifeTime: Float
        get() =  analysis.getValue(MWEntityAnimationType.HUMAN).lifeTime


    override fun getActionProperties(entityAnimationType: MWEntityAnimationType) : IDataActionProperties {
        return analysis.getValue(entityAnimationType)
    }


    final override val animation: Animation<TextureRegion>?


    init {
        val texture = AManaWars.m.getImageHandler().getTextureRegionSkill(texturePath)
        val textureWidth = texture.originalWidth / this.textureColumns
        val textureHeight = texture.originalHeight / this.textureRows

        val frames = com.badlogic.gdx.utils.Array<TextureRegion>(TextureRegion::class.java)
        frames.setSize(textureColumns * textureRows)
        for (x in 0 until textureColumns) {
            for (y in 0 until textureRows) {
                val region = TextureRegion(texture, x * textureWidth, -y * textureHeight, textureWidth, -textureHeight)
               // region.flip(false, true)
                frames[x + y * textureColumns] = region
            }
        }
        val animationspeed = 1f / (textureColumns * textureRows * animationFrequency)
        this.animation = Animation(animationspeed, frames)
    }
}