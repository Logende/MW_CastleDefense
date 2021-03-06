package org.neubauerfelix.manawars.manawars.entities.animation.human


import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeLegs
import java.lang.Error

open class BodyHumanAnimating(bodyDataHuman: IBodyDataHuman, scale: Float, sized: ISized) : BodyHuman(bodyDataHuman, scale, sized), IDrawable, ILogicable {

    companion object {
        val POSITION_COUNT_MAIN_NORMAL = 4
    }


    protected var positionCountMain = 4 //Can be changed by short animations and is reset when normal movements are animated
    protected var positionCountLegs = 4


    override fun doLogic(delta: Float) {
        val mirror = this.mirror
        armR.doLogic(sized, mirror, scale)
        body.doLogic(sized, mirror, scale)
        head.doLogic(sized, mirror, scale)
        armL.doLogic(sized, mirror, scale)
        footL.doLogic(sized, mirror, scale)
        footR.doLogic(sized, mirror, scale)
        if (shield != null) {
            shield!!.doLogic(sized, mirror, scale)
        }
        if (weapon != null) {
            weapon!!.doLogic(sized, mirror, scale)
        }
    }

    override fun draw(batcher: Batch) {
        val mirror = this.mirror
        armR.draw(batcher, sized, mirror, scale)
        body.draw(batcher, sized, mirror, scale)
        head.draw(batcher, sized, mirror, scale)
        armL.draw(batcher, sized, mirror, scale)
        footL.draw(batcher, sized, mirror, scale)
        footR.draw(batcher, sized, mirror, scale)
        if (shield != null) {
            shield!!.draw(batcher, sized, mirror, scale)
        }
        if (weapon != null) {
            weapon!!.draw(batcher, sized, mirror, scale)
        }
    }


    protected fun resetBodyParts() {
        head.update(bodyDataHuman.head.relativeX, bodyDataHuman.head.relativeY, bodyDataHuman.head.rotation)
        body.update(bodyDataHuman.body.relativeX, bodyDataHuman.body.relativeY, bodyDataHuman.body.rotation)
        armL.update(bodyDataHuman.armL.relativeX, bodyDataHuman.armL.relativeY, bodyDataHuman.armL.rotation)
        armR.update(bodyDataHuman.armR.relativeX, bodyDataHuman.armR.relativeY, bodyDataHuman.armR.rotation)

        footL.enabled = true
        footL.update(bodyDataHuman.footL.relativeX, bodyDataHuman.footL.relativeY, bodyDataHuman.footL.rotation)
        footR.enabled = true
        footR.update(bodyDataHuman.footR.relativeX, bodyDataHuman.footR.relativeY, bodyDataHuman.footR.rotation)

        if (shield != null) {
            shield.enabled = false
            //do not update shield height here because it is never changed
        }
        val w = weapon
        if (w != null) {
            w.setPosition(0)
            w.enabled = MConstants.ALWAYS_EQUIP_WEAPONS
            val weaponData = w.bodyPartData
            w.update(weaponData.relativeX, weaponData.relativeY, weaponData.rotation)
        }
        if (shield != null) {
            shield.enabled = MConstants.ALWAYS_WEAR_SHIELD
        }
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
                    footL.update(-20f)
                    footR.update(20f)
                    return
                }
                2 -> return
                3 -> {
                    footL.update(20f)
                    footR.update(-20f)
                    return
                }
            }

            MWAnimationTypeLegs.RIDE -> footR.enabled = false
        }
    }

    private fun animateBody(animation: MWAnimationTypeBody, position: Int, effect: MWAnimationTypeBodyEffect?) {

        // special case: should use shield but has different animation
        if (shield != null && shield.enabled && MConstants.ALWAYS_WEAR_SHIELD && animation != MWAnimationTypeBody.SHIELD) {
            // 1. play usual animation
            shield.enabled = false
            this.animateBody(animation, position, effect)

            // 2. update right arm and draw shield
            shield.enabled = true
            armR.update(-45f)
            return
        }

        when (animation) {
            MWAnimationTypeBody.NORMAL -> {

                // if unit carries weapon, why not continue showing weapon idle animation?
                val w = weapon
                if (w != null && w.enabled && MConstants.ALWAYS_EQUIP_WEAPONS) {
                    w.weaponType.animateIdle(this, w, position)
                    return
                }

                when (position) {
                    0 -> return
                    1 -> {
                        armL.update(5f)
                        armR.update(-5f)
                        return
                    }
                    2 -> {
                        armL.update(10f)
                        armR.update(-10f)
                        head.addLocation(0, 1)
                        return
                    }
                    3 -> {
                        armL.update(5f)
                        armR.update(-5f)
                        head.addLocation(0, 1)
                        return
                    }
                }
            }


            MWAnimationTypeBody.SHIELD -> {
                check(shield != null)
                shield!!.enabled = true
                armR.update(-45f)
                when (position) {
                    0 -> return
                    1 -> {
                        armL.update(2f)
                        return
                    }
                    2 -> {
                        armL.update(4f)
                        head.addLocation(0, 1)
                        return
                    }
                    3 -> {
                        armL.update(2f)
                        head.addLocation(0, 1)
                        return
                    }
                }
            }


            MWAnimationTypeBody.EFFECT -> when (effect) {

                MWAnimationTypeBodyEffect.WEAPON -> {
                    check(weapon != null)
                    weapon!!.enabled = true
                    this.weapon!!.weaponType.animateBodyEffect(this, this.weapon!!, position)
                    return
                }

                MWAnimationTypeBodyEffect.THROW -> {
                    armL.update(60f)
                    when (position) {
                        0 -> return
                        1 -> {
                            armL.update(120f)
                            return
                        }
                        2 -> {
                            armL.update(180f)
                            head.addLocation(0, 1)
                            armL.update(240f)
                            head.addLocation(0, 1)
                            return
                        }
                        3 -> {
                            armL.update(240f)
                            head.addLocation(0, 1)
                            return
                        }
                    }
                }

                MWAnimationTypeBodyEffect.SUMMON_SMALL -> when (position) {
                    0 -> {
                        armL.update(-20f)
                        return
                    }
                    1 -> {
                        armL.update(-40f)
                        return
                    }
                    2 -> {
                        armL.update(-60f)
                        head.addLocation(0, 1)
                        armL.update(-30f)
                        head.addLocation(0, 1)
                        return
                    }
                    3 -> {
                        armL.update(-30f)
                        head.addLocation(0, 1)
                        return
                    }
                }

                MWAnimationTypeBodyEffect.SUMMON_BIG -> {
                    armR.update(-30f)
                    armL.update(-30f)
                    when (position) {
                        0 -> return
                        1 -> {
                            armR.update(-70f)
                            armL.update(-70f)
                            return
                        }
                        2 -> {
                            armR.update(-45f)
                            armL.update(-45f)
                            head.addLocation(0, 1)
                            armR.update(-20f)
                            armL.update(-20f)
                            head.addLocation(0, 1)
                            return
                        }
                        3 -> {
                            armR.update(-20f)
                            armL.update(-20f)
                            head.addLocation(0, 1)
                            return
                        }
                    }
                }

                MWAnimationTypeBodyEffect.DROP -> {
                    armL.update(-35f)
                    when (position) {
                        0 -> return
                        1 -> {
                            armL.update(-10f)
                            return
                        }
                        2 -> {
                            armL.update(20f)
                            head.addLocation(0, 1)
                            armL.update(10f)
                            head.addLocation(0, 1)
                            return
                        }
                        3 -> {
                            armL.update(10f)
                            head.addLocation(0, 1)
                            return
                        }
                    }
                }

                MWAnimationTypeBodyEffect.UNBLOCK -> when (position) {
                    0 -> {
                        armR.update(-25f)
                        return
                    }
                }

                else ->  {}
            }
        }
    }


}
