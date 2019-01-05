package org.neubauerfelix.manawars.manawars.entities.animation.mount


import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeLegs

open class BodyMountAnimating(bodyDataMount: IBodyDataMount, scale: Float, sized: ISized) : BodyMount(bodyDataMount, scale, sized), IDrawable {

    companion object {
        val POSITION_COUNT_MAIN_NORMAL = 4
    }

    protected var positionCountMain = 4 //Can be changed by short animations and is reset when normal movements are animated
    protected var positionCountLegs = 4

    
    override fun draw(delta: Float, batcher: Batch) {
        val mirror = this.mirror
        footBL.draw(batcher, sized, mirror, scale)
        footBR.draw(batcher, sized, mirror, scale)
        body.draw(batcher, sized, mirror, scale)
        head.draw(batcher, sized, mirror, scale)
        footFL.draw(batcher, sized, mirror, scale)
        footFR.draw(batcher, sized, mirror, scale)
    }


    protected fun resetBodyParts() {
        head.update(bodyDataMount.head.relativeX, bodyDataMount.head.relativeY, bodyDataMount.head.rotation)
        body.update(bodyDataMount.body.relativeX, bodyDataMount.body.relativeY, bodyDataMount.body.rotation)
        footBL.update(bodyDataMount.footBL.relativeX, bodyDataMount.footBL.relativeY, bodyDataMount.footBL.rotation)
        footBR.update(bodyDataMount.footBR.relativeX, bodyDataMount.footBR.relativeY, bodyDataMount.footBR.rotation)
        footFL.update(bodyDataMount.footFL.relativeX, bodyDataMount.footFL.relativeY, bodyDataMount.footFL.rotation)
        footFR.update(bodyDataMount.footFR.relativeX, bodyDataMount.footFR.relativeY, bodyDataMount.footFR.rotation)

    }

    protected fun animate(animationLegs: MWAnimationTypeLegs, positionLegs: Int, animationBody: MWAnimationTypeBody,
                          positionBody: Int, animationBodyEffect: MWAnimationTypeBodyEffect?) {
        resetBodyParts()
        animateLegs(animationLegs, positionLegs)
        animateBody(animationBody, positionBody, animationBodyEffect)
    }


    private fun animateLegs(animation: MWAnimationTypeLegs, position: Int) {
        when (animation) {
            MWAnimationTypeLegs.STILL -> {
            }

            MWAnimationTypeLegs.WALK -> when (position) {
                0 -> return
                1 -> {
                    footBL.update(-20f)
                    footFL.update(30f)
                    footBR.update(-20f)
                    footFR.update(20f)
                    return
                }
                2 -> return
                3 -> {
                    footBL.update(30f)
                    footFL.update(-20f)
                    footBR.update(30f)
                    footFR.update(-20f)
                    return
                }
            }
        }
    }

    private fun animateBody(animation: MWAnimationTypeBody, position: Int, effect: MWAnimationTypeBodyEffect?) {
        when (animation) {
            MWAnimationTypeBody.NORMAL -> when (position) {
                0, 1 -> return
                2, 3 -> {
                    head.addLocation(0, 1)
                    return
                }
            }

            MWAnimationTypeBody.EFFECT -> when (effect) {

                MWAnimationTypeBodyEffect.NONE -> {
                }
            }
        }
    }




}
