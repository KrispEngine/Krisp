package engine.krisp.loop

import engine.krisp.glfw.Window
import kotlin.system.exitProcess

open class GameLoop(
    private val window: Window,
    private val gameLogicHandler: GameLogicHandler,
    private val updateTime: Long,
): Runnable {

    private var initializeTime: Long = System.currentTimeMillis()

    private val handleGameLoop = Runnable {

        var lastTime = System.nanoTime()
        var delta = 0.0
        var frames = 0
        var updates = 0
        var timer = System.currentTimeMillis()

        while (!window.shouldClose()) {
            val now = System.nanoTime()
            delta += (now - lastTime) / updateTime
            lastTime = now

            if (delta >= 1) {
                gameLogicHandler.handleGameLogic(deltaTime = delta)
                updates++
                delta--
            }

            window.forceUpdate();
            frames++
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000
                frames = 0
                updates = 0
            }
        }
    }

    fun getGameLoopActiveTime(): Long {
        return System.currentTimeMillis() - initializeTime
    }

    open fun runShutdownTasks() {
        window.onDisable()
        gameLogicHandler.runDisableTasks()
        exitProcess(0)
    }

    override fun run() {
        handleGameLoop.run()
        runShutdownTasks()
    }

}