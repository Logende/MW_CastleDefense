package org.neubauerfelix.manawars.castledefense.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.entities.CDEntityCastle
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.manawars.MManaWars

class CDPlayer(override val army: IDataArmy, override val controller: ICDController, override var team: Int) : ICDPlayer {

    override lateinit var castle: ICDEntityCastle
    override lateinit var enemy: ICDPlayer

    override lateinit var formation: ICDFormation

    override fun spawnCastle(leftSide: Boolean, mapWidth: Float) {
        val texture = MManaWars.m.getAssetLoader().getTexture(army.castle.textureName)
        val castleLocation = if (leftSide) {
            GameLocation(CDConstants.CASTLE_BORDER_OFFSET, GameConstants.CONTROLPANEL_HEIGHT)
        } else {
            GameLocation(mapWidth - texture.width - CDConstants.CASTLE_BORDER_OFFSET, GameConstants.CONTROLPANEL_HEIGHT)
        }

        val spawnLocation = GameLocation(castleLocation.x + texture.width/2, castleLocation.y).plus(army.castle.unitSpawnOffset)
        val direction = if (leftSide) 1 else -1
        this.castle = CDEntityCastle(castleLocation.x, castleLocation.y, TextureRegion(texture), direction, team,
                spawnLocation, army.castle.goldStart, army.castle.goldPerCharge)
        this.castle.spawn()

        this.formation = CDFormation(army.units, this)
        this.formation.spawn()
    }
}


