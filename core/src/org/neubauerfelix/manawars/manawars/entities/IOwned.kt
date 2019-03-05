package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity


/**
 * Some objects and entities which can be owned by an other certain entity implement this interface.
 * @author Felix Neubauer
 */
interface IOwned {

    companion object {
        //WARNING: will cause infinite recursive loop if entities are connected in an ownership cycle
        fun isOwnerOf(possibleOwner: IEntity, possibleOwned: IEntity, allowIndirectConnections: Boolean): Boolean {
            if (possibleOwned is IOwned) {
                if (possibleOwned.owner == possibleOwner) {
                    return true
                } else {
                    val owner = possibleOwned.owner
                    if (allowIndirectConnections && owner is IOwned) {
                        return isOwnerOf(possibleOwner, owner, true)
                    }
                }
            }
            return false
        }
    }


    var owner: IEntity
}
