package org.neubauerfelix.manawars.manawars.entities.animation.building

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import kotlin.math.min

/***
 * textureNameAnimation: the animation image is automatically split into 4 frames (horizontally split)
 */
open class EntityAnimationProducerBuilding(val textureNameAlive: String, val textureNameDead: String = textureNameAlive,
                                           val textureNameAnimation: String? = null,
                                           val animationFrameDuration: Float = 0f):
        IEntityAnimationProducer {
    override val animationType: MWEntityAnimationType = MWEntityAnimationType.BUILDING
    final override val bodyWidth: Int
    final override val bodyHeight: Int

    init {
        val texture = MManaWars.m.getImageHandler().getTextureRegionMain(textureNameAlive)
        bodyHeight = texture.originalHeight
        bodyWidth = texture.originalWidth
    }


    override val defaultScale: Float
        get() = 1f


    fun produceAnimationFrames(): Animation<TextureRegion>? {
        return if (textureNameAnimation == null) {
            null
        } else {
            val frames = MManaWars.m.getImageHandler().getTextureRegionsMain(textureNameAnimation, 4, 1)
            Animation(animationFrameDuration, *frames)
        }
    }

    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val animation = produceAnimationFrames()
        val body = BodyBuilding(entity, MManaWars.m.getImageHandler().getTextureRegionMain(textureNameAlive),
                MManaWars.m.getImageHandler().getTextureRegionMain(textureNameDead), animation)
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float,
                         weaponType: MWWeaponType?): IEntity {
        require(weaponType == null)
        val scale = min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( bodyWidth * scale, bodyHeight * scale)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyBuilding(rectangle, MManaWars.m.getImageHandler().getTextureRegionMain(textureNameAlive),
                MManaWars.m.getImageHandler().getTextureRegionMain(textureNameDead), null)
        return EntityAnimationAny(body, animationType)
    }


}