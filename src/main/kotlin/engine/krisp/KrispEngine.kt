package engine.krisp

import engine.krisp.loop.GameLoop
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object KrispEngine {

    private val logger = newDedicatedLogger("KrispEngine")

    fun startEngine(gameLoop: GameLoop) {
       gameLoop.run()
    }


    fun newDedicatedLogger(name: String): Logger = LogManager.getLogger(name)

}