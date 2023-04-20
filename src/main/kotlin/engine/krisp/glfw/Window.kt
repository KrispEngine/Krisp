package engine.krisp.glfw

import engine.krisp.KrispEngine
import engine.krisp.utils.Color
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import java.io.OutputStream

abstract class Window(
    title: String,
    width: Int,
    height: Int,
    private var keybindRegistry: KeybindRegistry? = null,
    resizable: Boolean = true,
) {

    private var backgroundColor: Color = Color.BLACK
    private var windowAddr: Long = 0L
    private val initializeTime: Long = System.currentTimeMillis()

    init {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit()) {
            val throwable = IllegalStateException("Unable to initialize GLFW")
            logger.error("Unable to initialize GLFW", throwable)
            throw throwable
        }
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, if (resizable) GLFW_TRUE else GLFW_FALSE)
        this.windowAddr = glfwCreateWindow(width, height, title, 0L, 0L)
        if (windowAddr == 0L) {
            val throwable = IllegalStateException("Failed to create the GLFW window")
            logger.error("Failed to create the GLFW window", throwable)
            throw throwable
        }
        logger.debug("Window ${this.windowAddr} created with title $title")

        if (keybindRegistry != null) {
            glfwSetKeyCallback(this.windowAddr, keybindRegistry)
            logger.debug("Keybind registry ${keybindRegistry.hashCode()} registered to window ${this.windowAddr}")
        }
        logger.debug("Keybind registry ${keybindRegistry.hashCode()} registered to window ${this.windowAddr}")

        val stack = stackPush()
        val pWidth = stack.mallocInt(1)
        val pHeight = stack.mallocInt(1)
        glfwGetWindowSize(this.windowAddr, pWidth, pHeight)
        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        glfwSetWindowPos(
            this.windowAddr,
            (vidmode!!.width() - pWidth[0]) / 2,
            (vidmode.height() - pHeight[0]) / 2
        )
        logger.debug("Window ${this.windowAddr} resized to $width x $height")
        glfwMakeContextCurrent(this.windowAddr)
        glfwSwapInterval(1)
        glfwShowWindow(this.windowAddr)
        logger.debug("Window ${this.windowAddr} shown")
    }

    open fun onEnable() {}
    open fun onDisable() {
        glfwFreeCallbacks(this.windowAddr)
        glfwDestroyWindow(this.windowAddr)
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }
    open fun onUpdate() {
        GL.createCapabilities()
        clear()
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glfwSwapBuffers(this.windowAddr)
        glfwPollEvents()
    }

    fun setBackgroundColor(color: Color) {
        this.backgroundColor = color
    }

    fun setKeybindRegistry(keybindRegistry: KeybindRegistry) {
        glfwSetKeyCallback(this.windowAddr, keybindRegistry)
        this.keybindRegistry = keybindRegistry
        logger.debug("Keybind registry ${keybindRegistry.hashCode()} registered to window ${this.windowAddr}")
    }

    fun clear() {
        glClearColor(this.backgroundColor.red, this.backgroundColor.green, this.backgroundColor.blue, this.backgroundColor.alpha)
    }

    fun shouldClose(): Boolean {
        return glfwWindowShouldClose(this.windowAddr)
    }

    fun registerKeybind(keybind: Keybind) {
        this.keybindRegistry?.registerKeybind(keybind)
    }

    fun unregisterKeybind(keybind: Keybind) {
        this.keybindRegistry?.unregisterKeybind(keybind)
    }

    fun unregisterKeybind(keybind: Int) {
        this.keybindRegistry?.unregisterKeybind(keybind)
    }



    companion object {
        private val logger = KrispEngine.newDedicatedLogger("Window")

        fun newWindow(
            title: String,
            width: Int,
            height: Int,
            keybindRegistry: KeybindRegistry? = null,
            resizable: Boolean = true,
        ): Window {
            return object : Window(title, width, height, keybindRegistry, resizable) {}
        }
    }
}