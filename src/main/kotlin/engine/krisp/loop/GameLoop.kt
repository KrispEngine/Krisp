package engine.krisp.loop

import engine.krisp.glfw.Window
import kotlin.system.exitProcess

open class GameLoop(
    private val window: Window,
    private val gameLogicHandler: GameLogicHandler,
    private val updateTime: Long,
    private var basicGameLoopMethod: BasicGameLoopMethod = BasicGameLoopMethod.verySimplyMethod()
): Runnable {

    private var initializeTime: Long = System.currentTimeMillis()

    fun getGameLoopActiveTime(): Long {
        return System.currentTimeMillis() - initializeTime
    }

    open fun runShutdownTasks() {
        window.onDisable()
        gameLogicHandler.runDisableTasks()
        exitProcess(0)
    }

    override fun run() {
        basicGameLoopMethod.run(window, updateTime, gameLogicHandler)
        runShutdownTasks()
    }

}