package org.neubauerfelix.manawars.game.entities

import com.badlogic.gdx.math.Vector2

open class GameEntityMovable(width: Float, height: Float): GameEntity(width, height), IMovable, ILogicable {

    private var speed = Vector2()
    private var acceleration = Vector2()


    fun pasteMovable(e: IMovable, onlyTemporaryValues: Boolean) {
        super.pasteEntity(e, onlyTemporaryValues)
        speed = Vector2(e.speedX, e.speedY)
        acceleration = Vector2(e.accelerationX, e.accelerationY)
    }

    override var speedX: Float
        get() = speed.x
        set(value) { speed.x = value}

    override var speedY: Float
        get() = speed.y
        set(value) { speed.y = value}

    override var accelerationX: Float
        get() = acceleration.x
        set(value) { acceleration.x = value}

    override var accelerationY: Float
        get() = acceleration.y
        set(value) { acceleration.y = value}

    override val speedDirectionHorizontal: Int
        get() = if (speed.x == 0f) 0 else if (speed.x > 0) 1 else -1

    override val speedDirectionVertical: Int
        get() = if (speed.y == 0f) 0 else if (speed.y > 0) 1 else -1


    override fun move(delta: Float) {
        if (this.acceleration.x != 0.0f) {
            speedX = this.speed.x + this.acceleration.x * delta
        }
        if (this.acceleration.y != 0.0f) {
            speedY = this.speed.y + this.acceleration.y * delta
        }

        if (this.speed.x != 0.0f) {
            x += this.speed.x * delta
        }
        if (this.speed.y != 0.0f) {
            y += this.speed.y * delta
        }

    }


    override fun doLogic(delta: Float) {}


}
