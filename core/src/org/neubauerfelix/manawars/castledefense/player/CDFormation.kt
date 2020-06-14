package org.neubauerfelix.manawars.castledefense.player

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.GameEntityMovable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CDFormation(private val units: List<IDataUnit>, private val player: ICDPlayer) :
        GameEntityMovable(0f, GameConstants.BACKGROUND_HEIGHT), ICDFormation, IDrawable {


    override var direction: Int = player.castle.direction
    override var team: Int =  player.castle.team
    override var moveSpeed = 0f // speed of slowest entity in formation


    private var rangeBest = 0f // range which enables most units to use their action
    private var rangeFirst = 0f // range which enables at least one unit to use their action

    private var anchorX: Float
        get() { return if (direction == 1) this.right else this.left }
        set(value) {
            if (direction == 1) {
                this.right = value
            } else {
                this.left = value
            }
        }


    private val entities: MutableList<IControlled> = arrayListOf()
    private val relativeX: MutableMap<IControlled, Float> = linkedMapOf()

    // Allied entities should be added, when they touch the formation rectangle
    override fun addEntity(e: IControlled) {
        synchronized(entities) {
            synchronized(relativeX) {
                entities.add(e)
                updateFormation(entities.size == 1)
            }
        }
    }

    // Entities should be removed, when they die
    override fun removeEntity(e: IControlled) {
        synchronized(entities) {
            synchronized(relativeX) {
                entities.remove(e)
                this.relativeX.remove(e)
                updateFormation(true)
            }
        }
    }

    override fun isContained(e: IControlled): Boolean {
        return entities.contains(e)
    }

    // Entities should manually try to keep up with the assigned x
    override fun getAssignedX(e: IControlled): Float {
        require(entities.contains(e))
        return anchorX + relativeX[e]!!
    }

    fun updateFormation(updateAnchor: Boolean) {
        if (this.entities.isEmpty()) {

            // Empty and waiting for entities to spawn and join the formation
            val entities = player.controller.analysis.entities.filterIsInstance(IControlled::class.java)
                    .filter { !it.remove }
            val mostFar : IEntity = if (entities.isNotEmpty()) entities.first() else player.castle
            val left =  min(player.castle.left, mostFar.left)
            val right = max(player.castle.right, mostFar.right)
            this.width = right - left
            this.x = left
            rangeFirst = 0f
            rangeBest = 0f
            moveSpeed = 0f

        } else {
            // 1. Simply sort the units in the right order
            entities.sortBy { s ->
                if (s.remove) {
                }
                val priority = this.units.indexOf(s.data)
                priority * 10000 + s.getDistanceHor(anchorX)
            }

            // 2. Assign locations to each unit
            var x = 0f
            for (e in this.entities) {
                this.relativeX[e] = x - e.width/2f * direction
                val entityWidth = if (CDConstants.FORMATION_UNIT_WIDTH == -1f) {
                    e.width
                } else CDConstants.FORMATION_UNIT_WIDTH

                x += if (direction == 1) {
                    -entityWidth - CDConstants.FORMATION_UNIT_DISTANCE
                } else {
                    entityWidth + CDConstants.FORMATION_UNIT_DISTANCE
                }
            }
            val oldAnchorX = this.anchorX
            width = Math.abs(x) // Note: width includes all units + 1x offset
            this.anchorX = oldAnchorX

            rangeFirst = 0f
            rangeBest = Float.MAX_VALUE
            moveSpeed = Float.MAX_VALUE
            // 3. Upgrade formation ranges
            relativeX.forEach { e, x ->
                val range = e.data.action.rangeMax - abs(x)
                rangeFirst = max(rangeFirst, range)
                rangeBest = min(rangeBest, range)
                moveSpeed = min(moveSpeed, e.walkSpeedMax)
            }

            // 4. Update Formation anchor: every time the units change, the formation anchor is set to x of furthest unit
            if (updateAnchor) {
                val furthest = relativeX.keys.sortedByDescending {
                    it.getDistanceHor(player.castle)
                }.first()
                if (this.direction == 1) {
                    this.right = furthest.right
                } else {
                    this.left = furthest.left
                }
            }
        }
    }

    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        val goalDistance = max(CDConstants.FORMATION_ENEMY_DISTANCE_MIN, rangeBest)
        val furthestEnemyX = if (direction == 1) {
            min(this.player.enemy.controller.analysis.furthestX, this.player.enemy.formation.left)
        } else {
            max(this.player.enemy.controller.analysis.furthestX, this.player.enemy.formation.right)
        }
        val actualDistance = this.getDistanceHor(furthestEnemyX)
        speedX = if (actualDistance > goalDistance) {
             moveSpeed * direction
        } else {
            0f
        }

    }

    override fun draw(batcher: Batch) {
        batcher.draw(MManaWars.m.getImageHandler().getTextureRegionButton("back"), this.anchorX - 5f, this.y, 10f, 10f)
    }

    init {
        this.y = GameConstants.CONTROLPANEL_HEIGHT
        this.updateFormation(true)
    }



}