package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class DataLeagueLoaded(config: Configuration) : DataLeague() {

    override val name: String = config.getString("name")
    override val castles: List<IDataCastle>
    override val tribes: List<IDataTribe>

    override val unitHealthAvg: Float = config.getFloat("unit_health_average")
    override val bossHealthAvg: Float = config.getFloat("boss_health_average")
    override val unitSkillDamageAvg: Float = config.getFloat("unit_skill_damage_average")
    override val bossSkillDamageAvg: Float = config.getFloat("boss_skill_damage_average")
    override val unitCostAvg: Float = config.getFloat("unit_cost_average")
    override val bossCostAvg: Float = config.getFloat("boss_cost_average")
    override val startGoldAvg: Float = config.getFloat("castle_gold_start_average")
    override val goldPerSecondAvg: Float = config.getFloat("castle_gold_per_second_average")
    override val castleHealthAvg: Float = config.getFloat("castle_health_average")


    init {
        // castles need to be loaded before tribes, because tribes use castles
        castles = ArrayList()
        val castleConfigNames = config.getStringList("castles")
        for (castleConfigName in castleConfigNames) {
            val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$castleConfigName", true)
            castles.add(DataCastleLoaded(handlerConfig, this))
        }

        tribes = ArrayList()
        val tribeConfigNames = config.getStringList("tribes")
        for (tribeConfigName in tribeConfigNames) {
            val tribeConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$tribeConfigName", true)
            val tribe = DataTribeLoaded(tribeConfig, this)
            tribes.add(tribe)
            println("Loaded tribe ${tribe.name}.")
        }

    }

    override fun getTribe(name: String): IDataTribe? {
        return tribes.filter { it.name.equals(name, ignoreCase = true) }.first()
    }

    override fun getCastle(name: String): IDataCastle? {
        return castles.filter { it.name.equals(name, ignoreCase = true) }.first()
    }


}