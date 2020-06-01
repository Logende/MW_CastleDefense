package org.neubauerfelix.manawars.tools

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonWriter
import org.neubauerfelix.manawars.game.events.EntityLivingSpawnEvent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.events.EntityDamageEvent
import org.neubauerfelix.manawars.manawars.events.EntityHealEvent


class EventWriter {

    open class EvaluationEventEntity(
            val event: String,
            val unitType: String,
            val health: Float,
            val entityX: Float,
            val team: Int,
            val time: Long
    )

    class EvaluationEventEntityDamage(
            event: String,
            unitType: String,
            health: Float,
            entityX: Float,
            team: Int,
            time: Long,
            val damage: Float,
            val deadly: Boolean,
            val cause: String
    ) : EvaluationEventEntity (event, unitType, health, entityX, team, time)

    class EvaluationEventEntityHeal(
            event: String,
            unitType: String,
            health: Float,
            entityX: Float,
            team: Int,
            time: Long,
            val value: Float
    ) : EvaluationEventEntity (event, unitType, health, entityX, team, time)

    class EvaluationEventEntitySpawn(
            event: String,
            unitType: String,
            health: Float,
            entityX: Float,
            team: Int,
            time: Long
    ) : EvaluationEventEntity (event, unitType, health, entityX, team, time)


    companion object {

        private val json = Json()

        init {
            json.setOutputType(JsonWriter.OutputType.json)
        }

        fun writeDamageEvent(e: EntityDamageEvent, entity: IControlled) : Any {
            val data = entity.data
            val evaluationEvent = EvaluationEventEntityDamage(
                    e::class.java.name,
                    data.unitType.name,
                    entity.health,
                    entity.centerHorizontal,
                    entity.team,
                    MManaWars.m.screen.getGameTime(),
                    e.damage,
                    e.deadlyDamage,
                    e.cause.name
            )
            return evaluationEvent
        }

        fun writeHealEvent(e: EntityHealEvent, entity: IControlled) : Any {
            val data = entity.data
            val evaluationEvent = EvaluationEventEntityHeal(
                    e::class.java.name,
                    data.unitType.name,
                    entity.health,
                    entity.centerHorizontal,
                    entity.team,
                    MManaWars.m.screen.getGameTime(),
                    e.value
            )
            return evaluationEvent
        }

        fun writeSpawnEvent(e: EntityLivingSpawnEvent, entity: IControlled) : Any {
            val data = entity.data
            val evaluationEvent = EvaluationEventEntitySpawn(
                    e::class.java.name,
                    data.unitType.name,
                    entity.health,
                    entity.centerHorizontal,
                    entity.team,
                    MManaWars.m.screen.getGameTime()
            )
            return evaluationEvent
        }
    }
}