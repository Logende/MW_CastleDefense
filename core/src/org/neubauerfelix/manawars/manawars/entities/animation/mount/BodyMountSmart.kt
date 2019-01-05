package org.neubauerfelix.manawars.manawars.entities.animation.mount


import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.IJumpable
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.animation.IBody
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeLegs
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class BodyMountSmart(bodyData: IBodyDataMount, scale: Float = 1f, sized: ISized) : BodyMountAnimating(bodyData, scale, sized), IBody {

    private var positionBody = 0
    private var positionLegs = 0
    private var animationBody = MWAnimationTypeBody.NORMAL
    private var animationLegs = MWAnimationTypeLegs.STILL

    private var next = 0f

    private var currentEffect: MWAnimationTypeBodyEffect? = null //Type of short animation. Null if none is played at the moment.


    init {
        updateAnimationType(true, true)
    }

    val isPlayingEffect: Boolean
        get() = currentEffect != null

    override val canFly: Boolean
        get() = false


    override fun draw(delta: Float, batcher: Batch) {
        next -= delta
        if (next < 0) {
            next = MConstants.MOUNT_ANIMATION_SPEED
            nextPosition()
        }
        super.draw(delta, batcher)
    }



    /**
     * Plays a short animation.
     * @param currentEffect Animation to play.
     */
    @Synchronized
    override fun playEffect(currentEffect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?) {
        this.currentEffect = currentEffect
        this.positionBody = 0
        this.updateAnimationType(true, false)
    }

    override fun update() {
        this.updateAnimationType(true, true)
    }

    fun updateAnimationType(updateBody: Boolean, updateLegs: Boolean){
        val sized = this.sized

        if (updateLegs) {
            var animationLegs = MWAnimationTypeLegs.STILL
            if ((sized !is IJumpable || sized.isOnGround) && sized is IMovable) {
                if (sized.speedX != 0f) {
                    animationLegs = MWAnimationTypeLegs.WALK
                }
            }
            //TODO: Check if riding and needs LEGS_RIDE
            this.animationLegs = animationLegs
            this.positionLegs = 0
        }

        if(updateBody) {
            var animationBody = MWAnimationTypeBody.NORMAL
            if (currentEffect != null) {
                animationBody = MWAnimationTypeBody.EFFECT
            } else {
                //TODO: Check whether can block and needs shield
            }
            this.animationBody = animationBody
            this.positionBody = 0
        }

        mirror = (if (sized is ILooking) sized.direction==-1 else false)
        next = MConstants.HUMAN_ANIMATION_SPEED
    }

    @Synchronized
    private fun nextPosition() {
        positionBody++
        positionLegs++
        if (positionBody >= positionCountMain) {
            positionBody = 0
            //Upper body animation is finished.
            if (currentEffect != null) {
                currentEffect = null
                positionCountMain = (BodyHumanAnimating.POSITION_COUNT_MAIN_NORMAL)
            }
        }
        if (positionLegs >= positionCountLegs) {
            positionLegs = 0
        }
        setPosition(positionBody, positionLegs)
    }

    private fun setPosition(positionBody: Int, positionLegs: Int) {
        this.positionBody = positionBody
        this.positionLegs = positionLegs
        animate(animationLegs, positionLegs, animationBody, positionBody, currentEffect)
    }


}