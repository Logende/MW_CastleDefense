package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.DataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType

enum class CDKILabel {

    UNIT_BOSS {
        override fun getDataInstance(player: ICDPlayer): Any {
            return player.tribe.army.units[MWUnitType.BOSS.index]
        }

        override fun getCost(player: ICDPlayer): Int {
            return (getDataInstance(player) as DataUnit).cost
        }

        override fun perform(player: ICDPlayer) {
            val data = getDataInstance(player) as DataUnit
            player.spawnUnit(data)
        }
    },
    UNIT_KNIGHT {
        override fun getDataInstance(player: ICDPlayer): Any {
            return player.tribe.army.units[MWUnitType.KNIGHT.index]
        }

        override fun getCost(player: ICDPlayer): Int {
            return (getDataInstance(player) as DataUnit).cost
        }

        override fun perform(player: ICDPlayer) {
            val data = getDataInstance(player) as DataUnit
            player.spawnUnit(data)
        }
    },
    UNIT_MELEE {
        override fun getDataInstance(player: ICDPlayer): Any {
            return player.tribe.army.units[MWUnitType.MELEE.index]
        }

        override fun getCost(player: ICDPlayer): Int {
            return (getDataInstance(player) as DataUnit).cost
        }

        override fun perform(player: ICDPlayer) {
            val data = getDataInstance(player) as DataUnit
            player.spawnUnit(data)
        }
    },
    UNIT_ARCHER {
        override fun getDataInstance(player: ICDPlayer): Any {
            return player.tribe.army.units[MWUnitType.ARCHER.index]
        }

        override fun getCost(player: ICDPlayer): Int {
            return (getDataInstance(player) as DataUnit).cost
        }

        override fun perform(player: ICDPlayer) {
            val data = getDataInstance(player) as DataUnit
            player.spawnUnit(data)
        }
    },
    UNIT_MAGE {
        override fun getDataInstance(player: ICDPlayer): Any {
            return player.tribe.army.units[MWUnitType.MAGE.index]
        }

        override fun getCost(player: ICDPlayer): Int {
            return (getDataInstance(player) as DataUnit).cost
        }

        override fun perform(player: ICDPlayer) {
            val data = getDataInstance(player) as DataUnit
            player.spawnUnit(data)
        }
    },

    NONE {
        override fun getDataInstance(player: ICDPlayer): Any {
            return 0.0
        }

        override fun getCost(player: ICDPlayer): Int {
            return 0
        }

        override fun perform(player: ICDPlayer) {
        }
    }
    /*BUILDING_ATTACK, // those will be added later
    BUILDING_STATUS,
    BUILDING_WALL,
    BUILDING_GOLD*/
    ;

    abstract fun getDataInstance(player: ICDPlayer): Any
    abstract fun getCost(player: ICDPlayer): Int
    abstract fun perform(player: ICDPlayer)

}