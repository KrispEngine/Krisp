package engine.krisp.loop

import engine.krisp.glfw.Window
import kotlin.system.exitProcess

open class UpdateBasedGameLoop(
    override val window: Window,
    override val gameLogicHandler: GameLogicHandler,
    private val updateTime: Long
): GameLoop {

    private var startTime = System.currentTimeMillis()
    private var lastTime = System.nanoTime()
    private var delta = 0.0
    private var frames = 0
    private var updates = 0
    private var timer = System.currentTimeMillis()

    override fun getGameLoopActiveTime(): Long {
        return System.currentTimeMillis() - startTime
    }

    override fun run() {

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

    override fun runShutdown() {
        window.onDisable()
        gameLogicHandler.runDisableTasks()
        exitProcess(0)
    }


}