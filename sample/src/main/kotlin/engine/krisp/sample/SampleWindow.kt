package engine.krisp.sample

import engine.krisp.glfw.Keybind
import engine.krisp.glfw.KeybindRegistry
import engine.krisp.glfw.Window
import engine.krisp.utils.Color
import org.lwjgl.glfw.GLFW

class SampleWindow : Window("Krisp Engine", 640, 480) {

    init {
        setKeybindRegistry(KeybindRegistry(this))

        registerKeybind(Keybind(GLFW.GLFW_KEY_W) {
            it.setBackgroundColor(Color.randomColor())
        })
    }

    override fun onEnable() {
        super.onEnable()
        setBackgroundColor(Color.randomColor())
    }
    override fun onDisable() {
        super.onDisable()
        logger.debug("Window ${this.windowAddr} disabled")
    }

}