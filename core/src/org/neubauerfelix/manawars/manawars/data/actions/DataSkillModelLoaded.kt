package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataSkillModelLoaded(override val name: String, config: Configuration) : IDataSkillModel {


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
     * Classification: Skillclass, range and other properties
     */
    override val skillClass: MWSkillClass = MWSkillClass.valueOf(config.getString("skillclass"))



    override var animation: Animation<TextureRegion>? = null
        private set


    override val displayColor: Color
        get() = skillClass.color





    override fun loadAsset() {
        AManaWars.m.getAssetLoader().loadTexture("skills/$texturePath.png")
    }

    override fun loadedAsset() {
        val texture = AManaWars.m.getAssetLoader().getTexture("skills/$texturePath.png")
        // texture.textureData.prepare()
        // val pixmap = texture.textureData.consumePixmap()

        val textureWidth = texture.width / this.textureColumns
        val textureHeight = texture.height / this.textureRows

        val frames = com.badlogic.gdx.utils.Array<TextureRegion>(TextureRegion::class.java)
        frames.setSize(textureColumns * textureRows)
        for (x in 0 until textureColumns) {
            for (y in 0 until textureRows) {
                val region = TextureRegion(texture, x * textureWidth, y * textureHeight, textureWidth, textureHeight)
                region.flip(false, true)
                frames[x + y * textureColumns] = region
            }
        }
        val animationspeed = 1f / (textureColumns * textureRows * animationFrequency)
        this.animation = Animation(animationspeed, frames)
    }

    override fun disposeAsset() {
        /*val entities = GameEntityHandler.getEntities()
        synchronized(entities) {
            for (e in entities) {
                if (e is MSkill) {
                    if ((e as MSkill).getData() === this) {
                        e.setRemove(true)
                    }
                }
            }
        } TODO*/
        AManaWars.m.getAssetLoader().unloadAsset("skills/$texturePath.png")
        this.animation = null
    }

    override val loaded: Boolean
        get() = animation != null




    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}