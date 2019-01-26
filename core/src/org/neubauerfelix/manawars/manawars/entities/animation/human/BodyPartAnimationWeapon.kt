package org.neubauerfelix.manawars.manawars.entities.animation.human


import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class BodyPartAnimationWeapon(val weaponType: MWWeaponType, textures: Array<TextureRegion>, bodyPartData: IBodyPartData,
                              scale: Float) : BodyPartAnimation(textures, bodyPartData, scale)
