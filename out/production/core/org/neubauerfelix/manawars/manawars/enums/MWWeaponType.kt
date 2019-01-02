package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyPartAnimationWeapon

class MWWeaponType(private val weaponclass: MWWeaponClass, val textureName: String) {


    val positionCount: Int
        get() = weaponclass.positionCount

    fun createBodyPart(bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
        return weaponclass.createBodyPart(this, bodyData, scale)
    }

    fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
        weaponclass.animateBodyEffect(body, weapon, position)
    }
}
