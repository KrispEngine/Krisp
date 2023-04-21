package engine.krisp.sample

import engine.krisp.loop.GameLogicHandler

internal class SampleGameLogic : GameLogicHandler() {
    override fun handleGameLogic(deltaTime: Double) {
        Main.logger.info("Game logic handled!")
    }

}