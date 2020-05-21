package org.neubauerfelix.manawars.tools


class EvaluationResultAdvanced : EvaluationResult(), IEvaluationResultAdvanced {

    override val events = mutableListOf<String>()
}