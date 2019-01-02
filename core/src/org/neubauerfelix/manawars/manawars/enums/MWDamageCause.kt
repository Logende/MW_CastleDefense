package org.neubauerfelix.manawars.manawars.enums

/**
 * Cause of damage dealt to an entity.
 * @author Felix Neubauer
 */
enum class MWDamageCause {
    /** Skills are the main damage source.	  */
    SKILL,

    /** Animations (like explosions caused by the bomb skill) can cause damage.  */
    ANIMATION,

    /** Damage caused by stateffects like poison or burning.  */
    STATEEFFECT,

    /** Damaged/killed because the tribe the entity belongs to was disposed.  */
    TRIBE_DISPOSED,

    /** Unit killed because the owner of the unit summoned a new unit of the same time although reaching the unit limit.  */
    SUMMON_LIMIT_EXCEEDED,

    OTHER


}