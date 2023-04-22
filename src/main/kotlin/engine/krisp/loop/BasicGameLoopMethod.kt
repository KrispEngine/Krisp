package engine.krisp.loop

import engine.krisp.glfw.Window

interface BasicGameLoopMethod {

    fun run(window: Window, updateTime: Long, gameLogicHandler: GameLogicHandler)

    companion object {

        fun verySimplyMethod(): BasicGameLoopMethod {
            return VerySimpleMethod()
        }

    }


}

internal class VerySimpleMethod: BasicGameLoopMethod {

    private var lastTime = System.nanoTime()
    private var delta = 0.0
    private var frames = 0
    private var updates = 0
    private var timer = System.currentTimeMillis()

    override fun run(window: Window, updateTime: Long, gameLogicHandler: GameLogicHandler) {
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

}