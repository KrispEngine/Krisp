package engine.krisp.sample

import engine.krisp.glfw.Keybind
import engine.krisp.glfw.KeybindRegistry
import engine.krisp.glfw.Window
import engine.krisp.texture.Texture
import engine.krisp.utils.Color
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11.*

class SampleWindow : Window("Krisp Engine", 640, 480) {

    private val sampleTextureFile = Main.getSampleTextureFile();
    private var sampleTexture: Texture? = null

    init {
        setKeybindRegistry(KeybindRegistry(this))
        registerKeybind(Keybind(GLFW.GLFW_KEY_W) {
            it.setBackgroundColor(Color.randomColor())
        })
    }

    override fun onEnable() {
        super.onEnable()
        setBackgroundColor(Color.randomColor())
        glEnable(GL_TEXTURE_2D)
        sampleTexture = Texture(sampleTextureFile)
    }

    override fun onUpdate() {
        if (sampleTexture != null) sampleTexture!!.bind()
        glTexCoord2d(0.0, 0.0)
        glVertex2d(0.0, 0.0)
        glTexCoord2d(1.0, 0.0)
        glVertex2d(1.0, 0.0)
        glTexCoord2d(1.0, 1.0)
        glVertex2d(1.0, 1.0)
        glTexCoord2d(0.0, 1.0)
        glVertex2d(0.0, 1.0)
    }

    override fun onDisable() {
        super.onDisable()
        logger.debug("Window ${this.windowAddr} disabled")
    }

}