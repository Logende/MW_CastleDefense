package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyPartAnimationWeapon


enum class MWWeaponClass {
    BOW {
        override fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
            val textures = generateTextures("char.bow." + weaponType.textureName, 4)
            val bodyPartData = BodyPartData(textures[0], 47f, 54f, 0f, 0f, 0f, bodyData, 0, 0 ,0 ,0)
            return BodyPartAnimationWeapon(weaponType, textures, bodyPartData, scale)
        }

        override fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(-90f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(1)
                    body.updateArms(-60f, -70f)
                    return
                }
                2 -> {
                    weapon.setPosition(2)
                    body.updateArms(-40f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(3)
                    body.updateArms(-30f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

        override fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    body.updateArms(5f, -70f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    body.updateArms(10f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    body.updateArms(5f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }
    },

    CROSSBOW {
        override fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
            val sprites = generateTextures("char.crossbow." + weaponType.textureName, 4)
            val bodyPartData = BodyPartData(sprites[0], 81f, 50f, 0f, 0f, 0f, bodyData, 0, 0 ,0 ,0)
            return BodyPartAnimationWeapon(weaponType, sprites, bodyPartData, scale)
        }

        override fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            body.armR.update(-70f)
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    return
                }
                1 -> {
                    weapon.setPosition(1)
                    body.armL.update(5f)
                    return
                }
                2 -> {
                    weapon.setPosition(2)
                    body.armL.update(10f)
                    body.head.addLocation(0, 1)
                    weapon.setPosition(3)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(3)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

        override fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    body.updateArms(5f, -70f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    body.updateArms(10f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    body.updateArms(5f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }
    },

    HORN {
        override val positionCountBodyEffect: Int
            get() = 16

        override fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
            val sprites = generateTextures("char.horn." + weaponType.textureName, 1)
            val bodyPartData = BodyPartData(sprites[0], 81f, 50f, 0f, 0f, 0f, bodyData, 0, 0 ,0 ,0)
            return BodyPartAnimationWeapon(weaponType, sprites, bodyPartData, scale)
        }

        override fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            body.armR.update(-90f)
            when (position % 4) {
                0 -> {
                    weapon.setPosition(0)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    body.armL.update(5f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    body.armL.update(10f)
                    body.head.addLocation(0, 1)
                    weapon.setPosition(0)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

        override fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    body.updateArms(5f, -70f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    body.updateArms(10f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    body.updateArms(5f, -70f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

    },

    WAND {
        override fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
            val sprites = generateTextures("char.wand." + weaponType.textureName, 1)
            val bodyPartData = BodyPartData(sprites[0], 85f, 50f, 12f, 50f, 30f, bodyData, 0, 0 ,0 ,0)
            return BodyPartAnimationWeapon(weaponType, sprites, bodyPartData, scale)
        }

        override fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    weapon.update(38f)
                    weapon.addLocation(0, 2)
                    body.updateArms(0f, -65f)
                    body.armL.update(5f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    weapon.update(42f)
                    weapon.addLocation(0, 4)
                    body.updateArms(0f, -60f)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    weapon.update(34f)
                    weapon.addLocation(0, 2)
                    body.updateArms(0f, -65f)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

        override fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 1)
                    body.updateArms(5f, -68f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 2)
                    body.updateArms(10f, -65f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 1)
                    body.updateArms(5f, -68f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

    },


    MISC_WEAPON {
        override fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
            val sprites = generateTextures("char.miscweapon." + weaponType.textureName, 1)
            val bodyPartData = BodyPartData(sprites[0], 45f, 50f, 50f, 50f, 0f, bodyData, 0, 0 ,0 ,0)
            return BodyPartAnimationWeapon(weaponType, sprites, bodyPartData, scale)
        }

        override fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            /*when (position) {
                0 -> {
                    weapon.setPosition(0)
                    weapon.update(10f)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    weapon.update(20f)
                    weapon.addLocation(0, 10)
                    body.updateArms(0f, -65f)
                    body.armL.update(5f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    weapon.update(30f)
                    weapon.addLocation(0, 20)
                    body.updateArms(0f, -60f)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    weapon.update(15f)
                    weapon.addLocation(0, 10)
                    body.updateArms(0f, -65f)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
            }*/
            animateIdle(body, weapon, position)
        }

        override fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 1)
                    body.updateArms(5f, -68f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 2)
                    body.updateArms(10f, -65f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 1)
                    body.updateArms(5f, -68f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

    },

    // Note: does not really look good (yet?)!
    MELEE {
        override fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon {
            val sprites = generateTextures("char.melee." + weaponType.textureName, 1)
            val bodyPartData = BodyPartData(sprites[0], 80f, 13f, 22f, 72f, 0f, bodyData, 0, 0 ,0 ,0)
            return BodyPartAnimationWeapon(weaponType, sprites, bodyPartData, scale)
        }

        override fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    weapon.update(10f)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    weapon.update(25f)
                    weapon.addLocation(0, 10)
                    body.updateArms(0f, -65f)
                    body.armL.update(5f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    weapon.update(40f)
                    weapon.addLocation(0, 20)
                    body.updateArms(0f, -60f)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    weapon.update(20f)
                    weapon.addLocation(0, 10)
                    body.updateArms(0f, -65f)
                    body.armL.update(5f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
            animateIdle(body, weapon, position)
        }

        override fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) {
            when (position) {
                0 -> {
                    weapon.setPosition(0)
                    body.updateArms(0f, -70f)
                    return
                }
                1 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 1)
                    body.updateArms(5f, -68f)
                    return
                }
                2 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 2)
                    body.updateArms(10f, -65f)
                    body.head.addLocation(0, 1)
                    return
                }
                3 -> {
                    weapon.setPosition(0)
                    weapon.addLocation(0, 1)
                    body.updateArms(5f, -68f)
                    body.head.addLocation(0, 1)
                    return
                }
            }
        }

    };

    open val positionCountBodyEffect: Int
        get() = 4

    abstract fun createBodyPart(weaponType: MWWeaponType, bodyData: IBodyData, scale: Float): BodyPartAnimationWeapon
    abstract fun animateBodyEffect(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int)
    abstract fun animateIdle(body: BodyHumanAnimating, weapon: BodyPartAnimationWeapon, position: Int) // idle always has a position count of 4

    protected fun generateTextures(textureName: String, columns: Int): Array<TextureRegion> {
        val all = AManaWars.m.getImageHandler().getTextureRegionMain(textureName)
        val width = all.regionWidth / columns
        val height = all.regionHeight
        return Array(columns){ i -> TextureRegion(all, width * i, 0, width, -height) }
    }
}
