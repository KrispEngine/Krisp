package engine.krisp.texture

import org.lwjgl.opengl.GL11.*
import org.lwjgl.stb.STBImage
import java.awt.image.BufferedImage
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Path
import javax.imageio.ImageIO

open class Texture(
    filePath: String
) {

    val id: Int
    val width: Int
    val height: Int
    val filePath: Path

    constructor(file: File) : this(file.absolutePath)
    constructor(filePath: Path) : this(filePath.toString())

    init {
        this.filePath = Path.of(filePath)
        val image: BufferedImage = ImageIO.read(this.filePath.toFile())
        this.width = image.width
        this.height = image.height
        this.id = glGenTextures()

        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST.toFloat()); // GL_NEAREST = no smoothing
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST.toFloat());

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, loadTexture(filePath));
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    companion object {

        fun loadTexture(filePath: String): ByteBuffer {
            val width = IntArray(1)
            val height = IntArray(1)
            val channels = IntArray(1)
            return STBImage.stbi_load(filePath, width, height, channels, 4)
                ?: throw RuntimeException("Failed to load image: $filePath")
        }
    }

}