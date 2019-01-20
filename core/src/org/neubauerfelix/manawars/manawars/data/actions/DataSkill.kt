package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.data.GameData
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.skills.Skill


abstract class DataSkill : GameData(), IDataSkill {

    final override var animation: Animation<TextureRegion>? = null
        private set

    override val displayName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val lifeTime: Float // TODO: generate good lifetime

    override val manaCost: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val rangeMax: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val rangeMin: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun init() {
        // TODO add sound and skill dependencies to asset list
    }

    override fun load() {
        AManaWars.m.getAssetLoader().loadTexture(this.texturePath)
    }

    override fun loaded() {
        val texture = AManaWars.m.getAssetLoader().getTexture(this.texturePath)
        // texture.textureData.prepare()
        // val pixmap = texture.textureData.consumePixmap()

        val textureWidth = texture.width / this.textureColumns
        val textureHeight = texture.height / this.textureRows

        val frames = com.badlogic.gdx.utils.Array<TextureRegion>(TextureRegion::class.java)
        frames.setSize(textureColumns * textureRows)
        for (x in 0 until textureColumns) {
            for (y in 0 until textureRows) {
                frames[x + y * textureColumns] = TextureRegion(texture, x * textureWidth, y * textureHeight, textureWidth, textureHeight)
                frames[x + y * textureColumns].flip(false, true)
            }
        }
        val animationspeed = 1f / (textureColumns * textureRows * animationFrequency)
        this.animation = Animation(animationspeed, frames)
    }

    override fun dispose() {
        AManaWars.m.getAssetLoader().unloadAsset(this.texturePath)
    }

    override fun disposed() {
        /*val entities = GameEntityHandler.getEntities()
        synchronized(entities) {
            for (e in entities) {
                if (e is Skill) {
                    if ((e as Skill).getData() === this) {
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
        return true
    }

    override fun action(owner: IActionUser): Boolean {
        return this.spawnSkill(owner) != null
    }

    override fun spawnSkill(owner: IActionUser): IEntity {
        val s = Skill(this, owner)
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
