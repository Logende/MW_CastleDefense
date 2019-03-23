package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.data.GameData
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.MSkill


abstract class DataSkill : GameData(), IDataSkill {

    final override var animation: Animation<TextureRegion>? = null
        private set

    override val displayColor: Color
        get() = skillClass.color

    override fun init() {
        // TODO add sound
        this.actionDependencies.forEach { dependency -> this.addAsset(dependency) }
    }

    override fun load() {
        AManaWars.m.getAssetLoader().loadTexture("skills/$texturePath.png")
    }

    override fun loaded() {
        val texture = AManaWars.m.getAssetLoader().getTexture("skills/$texturePath.png")
        // texture.textureData.prepare()
        // val pixmap = texture.textureData.consumePixmap()

        val textureWidth = texture.width / this.textureColumns
        val textureHeight = texture.height / this.textureRows

        val frames = com.badlogic.gdx.utils.Array<TextureRegion>(TextureRegion::class.java)
        frames.setSize(textureColumns * textureRows)
        for (x in 0 until textureColumns) {
            for (y in 0 until textureRows) {
                val region = TextureRegion(texture, x * textureWidth, y * textureHeight, textureWidth, textureHeight)
                region.flip(false, true)
                frames[x + y * textureColumns] = region
            }
        }
        val animationspeed = 1f / (textureColumns * textureRows * animationFrequency)
        this.animation = Animation(animationspeed, frames)
    }

    override fun dispose() {
        AManaWars.m.getAssetLoader().unloadAsset("skills/$texturePath.png")
    }

    override fun disposed() {
        /*val entities = GameEntityHandler.getEntities()
        synchronized(entities) {
            for (e in entities) {
                if (e is MSkill) {
                    if ((e as MSkill).getData() === this) {
                        e.setRemove(true)
                    }
                }
            }
        } TODO*/
        this.animation = null
    }



    override fun canUse(owner: IActionUser): Boolean {
        if (targetEnemy) {
            if (MManaWars.m.getSkillSetupHandler().findTarget(this, owner) == null) {
                return false
            }
        }
        if (skillLimit > 0) {
            if (MManaWars.m.screen.getEntities { e ->
                        e is MSkill && e.owner == owner && e.data == this }
                            .size >= skillLimit) {
                return false
            }
        }
        return true
    }

    override fun action(owner: IActionUser): Boolean {
        return this.spawnSkill(owner) != null
    }

    override fun spawnSkill(owner: IActionUser): IEntity {
        val s = MSkill(this, owner)
        s.spawn()
        return s
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
