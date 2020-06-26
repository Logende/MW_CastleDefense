package org.neubauerfelix.manawars.manawars.entities.animation.pet

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import kotlin.math.min

class EntityAnimationProducerPet(override val skinName: String, textureRegion: TextureRegion, frames: Int,
                                 override val frameDuration: Float, override val defaultScale: Float = 1f):
        IEntityAnimationProducer, IBodyDataPet{


    override val textures: Array<TextureRegion> = Array(frames) {
        val frameWidth = textureRegion.regionWidth / frames
        val t = TextureRegion(textureRegion, it * frameWidth, 0, frameWidth, -textureRegion.regionHeight)
        t.flip(false, false)

        t
    }

    override val bodyWidth: Int = (textures.first().regionWidth * defaultScale).toInt()
    override val bodyHeight: Int = (textures.first().regionHeight * defaultScale).toInt()

    private fun createAnimation() : Animation<TextureRegion> {
        val animation = Animation(frameDuration, *textures)
        animation.playMode = Animation.PlayMode.LOOP
        return animation
    }

    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val body = BodyPet(createAnimation(), scale, entity)
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float,
                         weaponType: MWWeaponType?): IEntity {
        val scale = min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( availableWidth, availableHeight)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyPet(createAnimation(), scale, rectangle)
        require(weaponType == null)
        return EntityAnimationAny(body, animationType)
    }

    override val animationType: MWEntityAnimationType = MWEntityAnimationType.PET
}