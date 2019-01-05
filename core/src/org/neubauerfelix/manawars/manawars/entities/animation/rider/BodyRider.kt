package org.neubauerfelix.manawars.manawars.entities.animation.rider

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.animation.IBody
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class BodyRider(sized: ISized, producerMount: IEntityAnimationProducer, producerHuman: IEntityAnimationProducer,
                scaleMount: Float, scaleHuman: Float):
        IBody, ISized by sized {


    private val rectMount: GameRectangle
    private val rectHuman: GameRectangle
    val mount: IEntityAnimation
    val human: IEntityAnimation


    override fun explode() {
    }

    override fun deadlyHit(killer: IMovable) {
    }

    override fun deadlyHit() {
    }

    override fun playEffect(effect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?) {
        human.playBodyEffect(effect, weaponType)
    }

    override fun update() {
        human.update()
        mount.update()
    }

    override val canFly: Boolean
        get() = mount.canFly

    override val scale: Float
        get() = mount.scale

    init {
        rectMount = GameRectangle(0f,0f,producerMount.bodyWidth * scaleMount,producerMount.bodyHeight * scaleMount)
        mount = producerMount.produce(rectMount, scaleMount)

        rectHuman = GameRectangle(0f,0f,producerHuman.bodyWidth * scaleHuman,producerHuman.bodyHeight * scaleHuman)
        human = producerHuman.produce(rectHuman, scaleHuman)
    }



    override fun getCollisionType(intersection: ISized): MWCollisionType {
        val collisionTypeHuman = human.getCollisionType(intersection)
        if (collisionTypeHuman != MWCollisionType.NONE) {
            return collisionTypeHuman
        }
        val collisionTypeMount = mount.getCollisionType(intersection)
        if (collisionTypeMount != MWCollisionType.NONE) {
            return collisionTypeMount
        }
        return MWCollisionType.NONE
    }

    override fun draw(delta: Float, batcher: Batch) {
        rectMount.x = this.x
        rectMount.bottom = this.bottom
        rectHuman.centerHorizontal = this.centerHorizontal + MConstants.RIDER_CENTRE_MOUNT_CENTRE_OFFSET_X
        rectHuman.bottom = mount.top + MConstants.RIDER_BOTTOM_MOUNT_TOP_OFFSET_Y
        mount.draw(delta, batcher)
        human.draw(delta, batcher)
        //TODO: make human leg animation riding and redraw mount head after drawing human
    }
}
