package engine.krisp.loop

import engine.krisp.glfw.Window

interface GameLoop {

    val window: Window
    val gameLogicHandler: GameLogicHandler

    fun getGameLoopActiveTime(): Long

    fun start() {
        run()
        runShutdown()
    }

    fun run()
    fun runShutdown()


}