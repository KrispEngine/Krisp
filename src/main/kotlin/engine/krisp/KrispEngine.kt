package engine.krisp

import engine.krisp.glfw.Window
import engine.krisp.loop.GameLogicHandler
import engine.krisp.loop.GameLoop
import org.apache.logging.log4j.LogManager

object KrispEngine {

    private val logger = newDedicatedLogger("KrispEngine")

    fun startEngine(gameLoop: GameLoop) {
       gameLoop.run()
    }


    fun newDedicatedLogger(name: String) = LogManager.getLogger(name)

}