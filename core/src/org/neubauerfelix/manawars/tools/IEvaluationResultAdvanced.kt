package org.neubauerfelix.manawars.tools



interface IEvaluationResultAdvanced : IEvaluationResult{

    val events: List<String> // format: <event class name>:<ordered event attributes separated by colon>
}