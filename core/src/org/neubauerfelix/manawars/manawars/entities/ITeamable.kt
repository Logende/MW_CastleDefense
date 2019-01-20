package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants

/**
 * Teamable objects belong to a certain team.
 * @author Felix Neubauer
 */
interface ITeamable {

    companion object {
        fun isTeamed(a: IEntity, b: IEntity) : Boolean {
            if (a is IOwned && a.owner != null) {
                return isTeamed(a.owner!!, b)
            }
            if (b is IOwned && b.owner != null) {
                return isTeamed(a, b.owner!!)
            }
            //Both identical?
            if (a == b) {
                return true
            }
            //One peaceful?
                if (a is ITeamable && a.team == MConstants.TEAM_PEACEFUL) {
                    return true
                }
            if (b is ITeamable && b.team == MConstants.TEAM_PEACEFUL) {
                return true
            }
            //Both have certain teams
            if (a is ITeamable && b is ITeamable) {
                val teamA = a.team
                val teamB = b.team
                //Both bots
                if (teamA == MConstants.TEAM_BOT || teamA == MConstants.TEAM_BOT_NEUTRAL) {
                    if(teamB == MConstants.TEAM_BOT || teamB == MConstants.TEAM_BOT_NEUTRAL) {
                        return true
                    }
                }
                //Both alone
                if (teamA == MConstants.TEAM_ALONE || teamB == MConstants.TEAM_ALONE) {
                    return false
                }

                //Same team?
                return teamA == teamB
            }
            return false
        }
    }

    var team: Int

}
