package org.neubauerfelix.manawars.manawars.entities.animation.human


import com.badlogic.gdx.graphics.g2d.Sprite
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class BodyPartAnimationWeapon(val weaponType: MWWeaponType, sprites: Array<Sprite>, bodyPartData: IBodyPartData,
                              scale: Float) : BodyPartAnimation(sprites, bodyPartData, scale)
