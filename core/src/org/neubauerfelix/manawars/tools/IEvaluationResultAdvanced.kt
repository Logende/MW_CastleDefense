package org.neubauerfelix.manawars.tools



interface IEvaluationResultAdvanced : IEvaluationResult{

    val events: List<Any> // list of simple event data classes containing important event information
}