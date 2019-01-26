package org.neubauerfelix.manawars.manawars.data.actions


import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.handlers.MathUtils

class DataSkillMixPart(val action: IDataSkill, private val offsetX: String, private val offsetY: String, private val offsetSpeedX: String, private val offsetSpeedY: String) {


    fun spawnSkill(owner: IActionUser): Boolean {
        val s = action.spawnSkill(owner)

        val offsetX = MathUtils.calcInt(this.offsetX, "r", Math.random())
        val offsetY = MathUtils.calcInt(this.offsetY, "r", Math.random())

        s.setLocation(s.x + offsetX * s.propertyScale, s.y + offsetY * s.propertyScale)

        val offsetSpeedX = MathUtils.calc(this.offsetSpeedX, "r", Math.random()).toFloat()
        val offsetSpeedY = MathUtils.calc(this.offsetSpeedY, "r", Math.random()).toFloat()

        if (s is IMovable) {
            s.speedX = (s.speedX + offsetSpeedX * s.propertyScale)
            s.speedY = (s.speedY + offsetSpeedY * s.propertyScale)
        }
        return true
    }

}
