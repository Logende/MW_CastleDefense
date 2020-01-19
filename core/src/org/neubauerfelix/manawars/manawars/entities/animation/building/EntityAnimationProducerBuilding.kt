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

open class EntityAnimationProducerBuilding(val textureNameAlive: String, val textureNameDead: String,
                                           val animationTextureNames: List<String>, val animationFrameDuration: Float,
                                           width: Int, height: Int, override val animationType: MWEntityAnimationType):
        IEntityAnimationProducer {

    override val bodyWidth: Int = width

    override val bodyHeight: Int = height

    override val defaultScale: Float
        get() = 1f


    fun produceAnimationFrames(): Animation<TextureRegion>? {
        val assetLoader = MManaWars.m.getAssetLoader()

        return if (animationTextureNames.isEmpty()) {
            null
        } else {
            val animationFrames = Array(animationTextureNames.size) {
                val name = animationTextureNames[it]
                assetLoader.createTextureRegion(name)
            }
            Animation(animationFrameDuration, *animationFrames)
        }
    }

    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val animation = produceAnimationFrames()
        val body = BodyBuilding(entity, MManaWars.m.getAssetLoader().createTextureRegion(textureNameAlive),
                MManaWars.m.getAssetLoader().createTextureRegion(textureNameDead), animation)
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float): IEntity {
        val scale = Math.min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( availableWidth, availableHeight)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyBuilding(rectangle, MManaWars.m.getAssetLoader().createTextureRegion(textureNameAlive),
                MManaWars.m.getAssetLoader().createTextureRegion(textureNameDead), null)
        return EntityAnimationAny(body, animationType)
    }


}