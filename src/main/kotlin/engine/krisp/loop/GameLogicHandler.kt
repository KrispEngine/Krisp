package engine.krisp.loop

abstract class GameLogicHandler {

    abstract fun handleGameLogic(deltaTime: Double)

    open fun runDisableTasks() {

    }

}