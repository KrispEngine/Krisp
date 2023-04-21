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
        KrispEngine.startEngine(
            GameLoop(
                SampleWindow(),
                SampleGameLogic(),
                1000L / 20L,
            )
        )
    }
}

fun main() {
    Main.onEnable()
}