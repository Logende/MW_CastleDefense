package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.*
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType


class CollisionHandler: ICollisionHandler {

    override fun updateCollisions(entities: List<IEntity>) {
        for (i in entities.indices) {
            for (j in i+1 until entities.size) {
                var a = entities[i]
                var b = entities[j]

                if (a is ICollidable && b is ICollidable) {

                    // If just one entity is a skill, then switch order. a should be the skill.
                    if (b is MSkill && a !is MSkill) {
                        val tmp = a
                        a = b
                        b = tmp
                    }

                    if (a is MSkill) {
                        if (b is MSkill && MConstants.SKILLS_COLLIDE_WITH_EACH_OTHER) {
                            // Two skills
                            if (!ITeamable.isTeamed(a, b)) {
                                if (ISized.overlaps(a, b)) {
                                    val collisionTypeA = a.getCollisionType(b)
                                    val collisionTypeB = b.getCollisionType(a)
                                    if (collisionTypeA != MWCollisionType.NONE && collisionTypeB != MWCollisionType.NONE) {
                                        a.collisionSkill(b)
                                    }
                                }
                            }
                        }

                        if (b is ILiving) {
                            // Living entity and skill
                            if (!ITeamable.isTeamed(a, b)) {
                                if (ISized.overlaps(a, b)) {
                                    val collisionTypeA = a.getCollisionType(b)
                                    val collisionTypeB = b.getCollisionType(a)
                                    if (collisionTypeA != MWCollisionType.NONE && collisionTypeB != MWCollisionType.NONE) {
                                        a.collisionEnemy(b, collisionTypeB)
                                    }
                                }
                            }
                        }

                        if (b is MEntityDestructiveWave) {
                            if (!ITeamable.isTeamed(a, b)) {
                                if (ISized.overlaps(a, b)) {
                                    b.collide(a)
                                }
                            }
                        }
                    }

                }
            }
        }
    }


}
