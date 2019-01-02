package org.neubauerfelix.manawars.manawars.handlers


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.ILoadableAsync
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.animation.SpecialAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.SpecialAnimationDamaging

class AnimationHandler: IAnimationHandler, ILoadableAsync {

    private lateinit var blood: Animation<TextureRegion>
    private lateinit var particles: Animation<TextureRegion>


    override fun loadedAssets() {
        val imageHandler = AManaWars.m.getImageHandler()
        blood = Animation(0.07f, *imageHandler.getTextureRegionsMain("effect.blood", 5, 1))
        particles = Animation(0.07f, *imageHandler.getTextureRegionsMain("effect.particles", 5, 1))
    }

    override fun isLoaded(): Boolean {
        return ::particles.isInitialized
    }

    override fun load() {
    }



    override fun playAnimation(animation: Animation<TextureRegion>, x: Float, y: Float, color: Color?): ISized {
        val a = SpecialAnimation(animation, color)
        a.x = x
        a.y = y
        a.spawn()
        return a
    }

    override fun playAnimationDamaging(animation: Animation<TextureRegion>, x: Float, y: Float, owner: IEntity, damage: Float, variableDamage: Boolean): ISized {
        val a = SpecialAnimationDamaging(animation, owner, damage, variableDamage)
        a.x = (x - a.width / 2f)
        a.y = (y - a.height / 2f)
        a.spawn()
        return a
    }

    override fun playBloodAnimation(x: Float, y: Float, color: Color?): ISized {
        return playAnimation(particles, x, y, color)
    }

    override fun playBloodAnimation(d: IEntity, color: Color?): ISized {
        val x = d.x + Math.random().toFloat() * d.width - blood.getKeyFrame(0f).regionWidth/2
        val y = d.y + Math.random().toFloat() * d.height - blood.getKeyFrame(0f).regionHeight/2
        return playBloodAnimation(x, y, color)
    }

}