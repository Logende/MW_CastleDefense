package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.Pools
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.game.events.EntityDamageEvent
import org.neubauerfelix.manawars.game.events.EntityGoldEvent
import org.neubauerfelix.manawars.game.events.Event
import org.neubauerfelix.manawars.game.events.Listener
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.MEntityTextStackable
import java.util.*
import kotlin.math.max
import kotlin.math.round

class TextVisualizationHandler : IHandler, IDisposable {
    private val pool: Pool<MEntityTextStackable> = Pools.get(MEntityTextStackable::class.java, 20)
    private val textStackable: MutableCollection<MEntityTextStackable> = HashSet()

    init {
        // Display entity damage
        MManaWars.m.getEventHandler().registerListener(EntityDamageEvent::class.java.name, object : Listener(){
            override fun handleEvent(event: Event) {
                val e = event as EntityDamageEvent
                if (!e.cancelled) {
                    //this@TextVisualizationHandler.displayHit(e.entity, e.damager, e.damage)
                }
            }
        })

        // TODO: Display gold earned (only of human player)
        MManaWars.m.getEventHandler().registerListener(EntityGoldEvent::class.java.name, object : Listener(){
            override fun handleEvent(event: Event) {
                val e = event as EntityGoldEvent
                if (!e.cancelled) {
                    this@TextVisualizationHandler.displayMoneyProduced(e.entity, e.goldDifference.toFloat())
                }
            }
        })
    }

    private fun displayHealthRestore(target: ISized, health: Float) {
        displayValueStackable(target, "[LIME]+", health, "health")
    }

    private fun displayMoneyProduced(target: ISized, money: Float) {
        displayValueStackable(target, "[GOLD]+", money, "money")
    }

    /**
     * Spawns a new damage label above the victim which is displayed for a short time.
     * @param victim Victim who took damage.
     * @param damager Damager who dealt damage.
     * @param damage Damage dealt.
     */
    private fun displayHit(victim: IEntity, damager: IEntity, damage: Float) {
        val x: Float
        val y: Float
        val offsetDirection = if (damager is ILooking) {
            (damager as ILooking).direction
        } else {
            if (victim.centerHorizontal < damager.centerHorizontal) +1 else -1
        }
        if (MConstants.HIT_VISUALIZATION_SHOW_ABOVE_HEAD and (victim.height <= 300)) {
            x = (victim.centerHorizontal + (30f + Math.random() * 30f) * offsetDirection).toFloat()
            y = (victim.top - 35f + Math.random() * 20f).toFloat()
        } else {
            x = (victim.centerHorizontal + (victim.width / 3f + Math.random() * victim.width / 6f) * offsetDirection).toFloat()
            y = ((damager.centerVertical + victim.centerVertical) / 2f - 20f + Math.random() * 40f).toFloat()
        }
        displayValueStackable(victim, x, y, "[RED]", damage, "damage", DISPLAY_DURATION)
    }

    private fun displayText(target: ISized, text: String, topic: String) {
        displayText(target, (target.centerHorizontal - 50f + Math.random() * 100f).toFloat(),
                (target.y - 20f - Math.random() * 50f).toFloat(), text, topic, DISPLAY_DURATION)
    }

    private fun displayText(target: ISized, x: Float, y: Float, text: String, topic: String, displayDuration: Int) {
        val t = pool.obtain()
        t.init(target, x, y, -1f, text, topic, displayDuration, ::freeValueStackable)
        t.spawn()
        textStackable.add(t)
    }

    private fun displayValueStackable(target: ISized, prefix: String, value: Float, topic: String) {
        displayValueStackable(target, (target.centerHorizontal - 50 + Math.random() * 100).toFloat(),
                (target.y - 20 - Math.random() * 50).toFloat(), prefix, value, topic, DISPLAY_DURATION)
    }

    private fun displayValueStackable(target: ISized, x: Float, y: Float, prefix: String, value: Float, topic: String,
                                      displayDuration: Int) {
        var v = value
        synchronized(textStackable) {
            for (t in textStackable) {
                if (t.target === target && (t.topic == topic) and !t.remove) {
                    v += t.value
                    t.remove = true
                }
            }
        }
        val text = prefix + getNumberDisplay(v, true)
        val t = pool.obtain()
        t.init(target, x, y, v, text, topic, displayDuration, ::freeValueStackable)
        t.spawn()
        textStackable.add(t)
    }

    private fun getNumberDisplay(value: Float, roundBigValues: Boolean): String {
        var v = value
        if (v.toInt().toFloat() == v || roundBigValues && v >= 1){
            return max(1f, round(v)).toString()
        }
        v = (v * 10).toInt().toFloat()
        return (v / 10).toString()
    }

    private fun freeValueStackable(t: MEntityTextStackable) {
        synchronized(textStackable) { textStackable.remove(t) }
        pool.free(t)
    }

    override fun dispose() {
        textStackable.clear()
        pool.clear()
    }

    companion object {
        private const val DISPLAY_DURATION = 1000
    }

}