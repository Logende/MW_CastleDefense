package org.neubauerfelix.manawars.castledefense.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.entities.CDEntityCastle
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.manawars.MManaWars

class CDPlayer(override val army: IDataArmy, override val controller: ICDController, override val team: Int) : ICDPlayer {

    override lateinit var castle: ICDEntityCastle
    override lateinit var enemy: ICDPlayer

    override fun spawnCastle(leftSide: Boolean, mapWidth: Float) {
        val texture = MManaWars.m.getAssetLoader().getTexture(army.castle.textureName)
        val spawnLocation = if (leftSide) {
            GameLocation(CDConstants.CASTLE_BORDER_OFFSET, GameConstants.CONTROLPANEL_HEIGHT)
        } else {
            GameLocation(mapWidth - texture.width - CDConstants.CASTLE_BORDER_OFFSET, GameConstants.CONTROLPANEL_HEIGHT)
        }.plus(army.castle.unitSpawnOffset)
        val direction = if (leftSide) 1 else -1
        this.castle = CDEntityCastle(spawnLocation.x, spawnLocation.y, TextureRegion(texture), direction)
        this.castle.spawn()
    }
}


