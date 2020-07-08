package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.castledefense.entities.CDEntityCastle
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.castledefense.events.EntityMoneyEvent
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class CDPlayer(override val tribe: IDataTribe, override val controller: ICDController, override var team: Int) : ICDPlayer {

    override lateinit var castle: ICDEntityCastle
    override lateinit var enemy: ICDPlayer

    override lateinit var formation: ICDFormation

    override val unitsToBuildNextCycle: MutableList<IDataUnit> = arrayListOf()


    private val unitsToBuildNow: MutableList<IDataUnit> = arrayListOf()
    private var nextUnitBuildTime = 0L

    override fun orderUnitToBuild(unit: IDataUnit) {
        require(castle.storedMoney >= unit.cost)
        unitsToBuildNextCycle.add(unit)
        castle.storedMoney -= unit.cost
    }

    override  fun cancelUnitToBuild(index: Int) {
        castle.storedMoney += unitsToBuildNextCycle[index].cost
        unitsToBuildNextCycle.removeAt(index)
    }

    override fun clearUnitsToBuild() {
        val moneyBack = unitsToBuildNextCycle.map { it.cost }.sum()
        unitsToBuildNextCycle.clear()
        castle.storedMoney += moneyBack
    }

    override fun spawnCastle(leftSide: Boolean, mapWidth: Float) {
        val data = tribe.castle
        val textureName = data.getTextureName(tribe.backgroundTheme)
        val texture = MManaWars.m.getImageHandler().getTextureRegionMain(textureName)
        val direction = if (leftSide) 1 else -1


        val castleLocation = if (leftSide) {
            GameLocation(CDConstants.CASTLE_BORDER_OFFSET,
                    GameConstants.WORLD_HEIGHT_UNITS - texture.originalHeight)
        } else {
            GameLocation(mapWidth - texture.originalWidth - CDConstants.CASTLE_BORDER_OFFSET,
                    GameConstants.WORLD_HEIGHT_UNITS - texture.originalHeight)
        }.plus(GameLocation(data.xOffset * direction, data.yOffset))

        val spawnLocation = GameLocation(castleLocation.x + texture.originalWidth/2,
                GameConstants.WORLD_HEIGHT_UNITS).
                plus(GameLocation(data.unitSpawnXOffset * direction, data.unitSpawnYOffset))

        this.castle = CDEntityCastle(castleLocation.x, castleLocation.y, textureName,
                data.health, direction, team, spawnLocation,
                data.moneyStart, data.moneyPerCycle, this)
        this.castle.spawn()

        this.formation = CDFormationStrategic(tribe.army.units, this)
        this.formation.spawn()
    }

    override fun executeUnitBuilding() {
        unitsToBuildNow.addAll(unitsToBuildNextCycle)
        unitsToBuildNextCycle.clear()
        val event = EntityMoneyEvent(castle, castle, castle.moneyPerCycle)
        MManaWars.m.getEventHandler().callEvent(event)
        if (!event.cancelled) {
            event.castle.storedMoney += event.moneyDifference
        }
    }

    override fun doLogic(delta: Float) {
        // build units who are waiting to be built
        synchronized(unitsToBuildNow) {
            if (unitsToBuildNow.isNotEmpty()) {
                val gameTime = MManaWars.m.screen.getGameTime()
                if (nextUnitBuildTime <= gameTime) {
                    this.spawnUnit(unitsToBuildNow.first())
                    unitsToBuildNow.removeAt(0)
                    nextUnitBuildTime = gameTime + CDConstants.CASTLE_SPAWN_UNIT_DELAY
                }
            }
        }

        // rest
        controller.doLogic(delta)
    }

    override fun load() {
        controller.load()
    }

    override fun dispose() {
        controller.dispose()
    }
}


