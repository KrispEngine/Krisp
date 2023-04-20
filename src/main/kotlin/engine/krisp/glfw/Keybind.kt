package engine.krisp.glfw

import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import java.util.function.Consumer

open class Keybind(
    private val key: Int,
    private val action: Consumer<Window>
) {

    fun isKeyMatching(key: Int): Boolean {
        return this.key == key
    }

    fun isKeyMatching(keybind: Keybind): Boolean {
        return this.key == keybind.key
    }

    fun onKey(window: Window, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key == this.key && action == GLFW_RELEASE) {
            this.action.accept(window)
        }
    }
}