package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IActionUser


/**
 * Basic interface of skill data; used by every skill. Acts like a "building plan" for skills. One building plan provides the
 * ability to spawn an unlimited amounts of skills of that type.
 * Contains every method needed to work with skills while the individual implementations execute all requires background tasks.
 * @author Felix Neubauer
 */
interface IDataSkill : IDataAction {


    val effect: IDataSkillEffect
    val model: IDataSkillModel
    val lifeTime: Float // Life time of skill in s (does not include idle time)


    /**
     * Executes the skill action which is for example spawning one skill or a set of other skills.
     * @param owner MSkill owner.
     * @return returns the created skill. If multiple skills were spawned only one of them is returned. In case of failure null is returned.
     */
    fun spawnSkill(owner: IActionUser): IEntity


}
