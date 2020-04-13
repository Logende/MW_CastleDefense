package org.neubauerfelix.manawars.castledefense.components

import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MImage

class CDComponentControlPanel : MImage(0f, 0f, GameConstants.SCREEN_WIDTH,
        GameConstants.CONTROLPANEL_HEIGHT,
        MManaWars.m.getImageHandler().getTextureRegionButton(GameConstants.CONTROLPANEL_TEXTURE))