package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

class CDKIFeatureExtractor : ICDKIFeatureExtractor {

    override fun generateFeatureVector(player: ICDPlayer): Array<Double> {
        val prep = CDKIFeaturePreparation()
        prep.prepare(player)

        val f1 = BaseFeatures.gold(player)
        val f2 = BaseFeatures.gold(player.enemy)
        val f3 = BaseFeatures.distancePlayerToEnemyCastle(player)
        val f4 = BaseFeatures.distancePlayerToEnemyCastle(player.enemy)
        val f5 = BaseFeatures.countUnit1(player)
        val f6 = BaseFeatures.countUnit2(player)
        val f7 = BaseFeatures.countUnit3(player)
        val f8 = BaseFeatures.countUnit4(player)
        val f9 = BaseFeatures.countUnit5(player)
        val f10 = BaseFeatures.countUnit1(player.enemy)
        val f11 = BaseFeatures.countUnit2(player.enemy)
        val f12 = BaseFeatures.countUnit3(player.enemy)
        val f13 = BaseFeatures.countUnit4(player.enemy)
        val f14 = BaseFeatures.countUnit5(player.enemy)
        val f15 = BaseFeatures.countUnitTotal(player)
        val f16 = BaseFeatures.countUnitTotal(player.enemy)
        val f17 = BaseFeatures.costUnit1(player)
        val f18 = BaseFeatures.costUnit2(player)
        val f19 = BaseFeatures.costUnit3(player)
        val f20 = BaseFeatures.costUnit4(player)
        val f21 = BaseFeatures.costUnit5(player)
        val f22 = BaseFeatures.costUnit1(player.enemy)
        val f23 = BaseFeatures.costUnit2(player.enemy)
        val f24 = BaseFeatures.costUnit3(player.enemy)
        val f25 = BaseFeatures.costUnit4(player.enemy)
        val f26 = BaseFeatures.costUnit5(player.enemy)
        val f27 = BaseFeatures.effectivenessUnit1(player, prep)
        val f28 = BaseFeatures.effectivenessUnit2(player, prep)
        val f29 = BaseFeatures.effectivenessUnit3(player, prep)
        val f30 = BaseFeatures.effectivenessUnit4(player, prep)
        val f31 = BaseFeatures.effectivenessUnit5(player, prep)
        val f32 = BaseFeatures.effectivenessUnit5(player, prep)
        val f33 = BaseFeatures.healthTotal(player)
        val f34 = BaseFeatures.healthTotal(player.enemy)
        val f35 = BaseFeatures.healthCastle(player)
        val f36 = BaseFeatures.healthCastle(player.enemy)

        val f37 = BaseFeatures.recentDamageDealt(prep)
        val f38 = BaseFeatures.recentDamageTaken(prep)
        val f39 = BaseFeatures.recentDeaths(prep)
        val f40 = BaseFeatures.recentKills(prep)
        return arrayOf(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19,
        f20, f21, f22, f23, f24, f25, f26, f27, f28, f29, f30, f31, f32, f33, f34, f35, f36, f37, f38, f39, f40)
    }
}