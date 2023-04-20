package engine.krisp.glfw

import org.lwjgl.glfw.GLFWKeyCallbackI

class KeybindRegistry(
    private var parentWindow: Window,
    private var allowDuplicateKeys: Boolean = false
): GLFWKeyCallbackI {

    private val keybinds: MutableSet<Keybind> = mutableSetOf()

    fun registerKeybind(keybind: Keybind): Boolean {
        if (allowDuplicateKeys) {
            return keybinds.add(keybind)
        }
        if (keybinds.any { it.isKeyMatching(keybind) }) {
            return false
        }
        return keybinds.add(keybind)
    }

    fun unregisterKeybind(keybind: Keybind): Boolean {
        return keybinds.remove(keybind)
    }

    fun unregisterKeybind(key: Int): Boolean {
        return keybinds.removeIf { it.isKeyMatching(key) }
    }

    fun shouldAllowDuplicateKeys(allow: Boolean): Boolean {
        this.allowDuplicateKeys = allow
        return this.allowDuplicateKeys
    }

    private fun getMatchingKeybinds(key: Int): Set<Keybind> {
        return keybinds.filter { it.isKeyMatching(key) }.toSet()
    }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        getMatchingKeybinds(key).forEach { it.onKey(parentWindow, key, scancode, action, mods) }
    }
}