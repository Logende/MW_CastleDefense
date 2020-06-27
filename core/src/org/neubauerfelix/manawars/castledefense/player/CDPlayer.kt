package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.castledefense.entities.CDEntityCastle
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class CDPlayer(override val tribe: IDataTribe, override val controller: ICDController, override var team: Int) : ICDPlayer {

    override lateinit var castle: ICDEntityCastle
    override lateinit var enemy: ICDPlayer

    override lateinit var formation: ICDFormation

    override val unitsToBuildNextCycle: MutableList<IDataUnit> = arrayListOf()


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

        this.formation = CDFormation(tribe.army.units, this)
        this.formation.spawn()
    }

    override fun executeUnitBuilding() {
        val totalCost = unitsToBuildNextCycle.map { it.cost }.sum()
        val moneyLeft = castle.moneyPerCycle - totalCost
        require(moneyLeft >= 0)
        unitsToBuildNextCycle.forEach {
            this.spawnUnit(it)
        }
        castle.storedMoney += moneyLeft
    }

    override fun load() {
        controller.load()
    }

    override fun dispose() {
        controller.dispose()
    }
}


