package org.neubauerfelix.manawars.manawars.entities.animation.human


import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.IJumpable
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.IRideable
import org.neubauerfelix.manawars.manawars.entities.animation.IBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeLegs
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType


class BodyHumanSmart(bodyData: IBodyDataHuman, sized: ISized, scale: Float = 1.0f): BodyHumanAnimating(bodyData, scale, sized), IBody {

    private var positionBody = 0
    private var positionLegs = 0
    private var animationBody = MWAnimationTypeBody.NORMAL
    private var animationLegs = MWAnimationTypeLegs.STILL

    private var next = MConstants.HUMAN_ANIMATION_SPEED

    private var currentEffect: MWAnimationTypeBodyEffect? = null //Type of short animation. Null if none is played at the moment.


    init {
        updateAnimationType(this, true, true)
    }

    val isPlayingEffect: Boolean
        get() = currentEffect != null


    override val canFly: Boolean
        get() = false


    override fun draw(delta: Float, batcher: Batch) {
        next -= delta
        if (next < 0) {
            next = MConstants.HUMAN_ANIMATION_SPEED
            nextPosition()
        }
        super.draw(delta, batcher)
    }


    /**
     * Plays a short animation.
     * @param currentEffect Animation to play.
     * @param weaponType Weapontype used by the animation. Can be null.
     */
    @Synchronized
    override fun playEffect(currentEffect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?) {
        this.currentEffect = currentEffect
        this.positionBody = 0
        if (weaponType != null) {
            this.positionCountMain = weaponType!!.positionCount
        } else {
            this.positionCountMain = (if (currentEffect == null) BodyHumanAnimating.POSITION_COUNT_MAIN_NORMAL else currentEffect!!.positionCount)
        }
        setWeapon(weaponType)
        this.updateAnimationType(this, true, false)
    }

    override fun updateAnimation(sized: ISized?) {
        this.updateAnimationType(sized,true, true)
    }

    fun updateAnimationType(sized: ISized?, updateBody: Boolean, updateLegs: Boolean){
        val sized = if (sized != null) sized else this.sized

        if (updateLegs) {
            var animationLegs = MWAnimationTypeLegs.STILL
            if ((sized !is IJumpable || sized.isOnGround) && sized is IMovable) {
                if (sized.speedX != 0f) {
                    animationLegs = MWAnimationTypeLegs.WALK
                }
            }
            if (sized is IRideable && sized.riding) {
                System.out.println("legs ride")
                animationLegs = MWAnimationTypeLegs.RIDE
            }
            this.animationLegs = animationLegs
            this.positionLegs = 0
        }

        if(updateBody) {
            var animationBody = MWAnimationTypeBody.NORMAL
            if (currentEffect != null) {
                animationBody = MWAnimationTypeBody.EFFECT
            } else {
               // Shields are not possible currently. Else shield code would be put in here
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
