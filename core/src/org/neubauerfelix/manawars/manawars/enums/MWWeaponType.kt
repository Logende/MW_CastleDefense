package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyPartAnimationWeapon

class MWWeaponType(private val weaponClass: MWWeaponClass, val textureName: String) {


    val positionCount: Int
        get() = weaponClass.positionCount

    fun createBodyPart(bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
        return weaponClass.createBodyPart(this, bodyData, scale)
    }

    fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
        weaponClass.animateBodyEffect(body, weapon, position)
    }
}
