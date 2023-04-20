package engine.krisp.sample

import engine.krisp.KrispEngine
import engine.krisp.glfw.Keybind
import engine.krisp.glfw.KeybindRegistry
import engine.krisp.glfw.Window
import engine.krisp.loop.GameLogicHandler
import engine.krisp.loop.GameLoop
import engine.krisp.sample.Main.logger
import engine.krisp.utils.Color
import org.lwjgl.glfw.GLFW.GLFW_KEY_W


object Main {
    internal val logger = KrispEngine.newDedicatedLogger("Main")

    internal fun onEnable() {
        val window = Window.newWindow(
            "Krisp Engine",
            640, 480
        )
        val keybindRegistry = KeybindRegistry(window)
        window.setKeybindRegistry(keybindRegistry)
        window.registerKeybind(Keybind(GLFW_KEY_W) {
            it.setBackgroundColor(Color.randomColor())
        })
        KrispEngine.startEngine(
            GameLoop(
                window,
                SampleGameLogic(),
                1000L / 20L,
            )
        )
    }
}

internal class SampleGameLogic : GameLogicHandler() {
    override fun handleGameLogic(deltaTime: Double) {
        logger.info("Game logic handled!")
    }

}

fun main() {
    Main.onEnable()
}