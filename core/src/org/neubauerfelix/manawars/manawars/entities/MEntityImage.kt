package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.GameEntityMovable

class MEntityImage(x: Float, y: Float, width: Float, height: Float, private val image: TextureRegion) :
        GameEntityMovable(width, height), IDrawable {


    var mirror: Boolean = false

    init {
        setLocation(x, y)
    }


    override fun draw(batcher: Batch) {
        if (mirror) {
            batcher.draw(image, x + width, y, -width/2, height/2f,  -width, height, 1f, 1f, rotation)
        } else {
            batcher.draw(image, x, y, width/2, height/2f, width, height, 1f, 1f, rotation)
        }
    }

}
