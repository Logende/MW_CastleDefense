package org.neubauerfelix.manawars.manawars.data.actions


import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.handlers.MathUtils

class DataSkillMixPart(val action: IDataSkill, private val offsetX: String, private val offsetY: String,
                       private val offsetSpeedX: String, private val offsetSpeedY: String,
                       private val offsetIdleTime: String) {


    fun spawnSkill(owner: IActionUser): Boolean {
        val s = action.spawnSkill(owner)

        val offsetX = MathUtils.calcInt(this.offsetX)
        val offsetY = MathUtils.calcInt(this.offsetY)

        s.setLocation(s.x + offsetX * s.propertyScale, s.y + offsetY * s.propertyScale)

        val offsetSpeedX = MathUtils.calc(this.offsetSpeedX).toFloat()
        val offsetSpeedY = MathUtils.calc(this.offsetSpeedY).toFloat()

        val offsetIdleTime = MathUtils.calc(this.offsetIdleTime).toFloat()

        if (s is IMovable) {
            s.speedX = (s.speedX + offsetSpeedX * s.propertyScale)
            s.speedY = (s.speedY + offsetSpeedY * s.propertyScale)
        }
        if (s is MSkill) {
            s.idleTimeLeft += offsetIdleTime
        }
        return true
    }

}
