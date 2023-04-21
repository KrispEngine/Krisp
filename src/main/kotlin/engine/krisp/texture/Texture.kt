package engine.krisp.texture

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER
import org.lwjgl.stb.STBImage.stbi_load
import org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

class Texture {

    private val id = glGenTextures()

    private var width = 0
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException("Height cannot be negative")
            }
            field = value
        }
    private var height = 0
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException("Height cannot be negative")
            }
            field = value
        }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    fun setParam(name: Int, value: Int) {
        glTexParameteri(GL_TEXTURE_2D, name, value)
    }

    fun uploadData(width: Int, height: Int, data: ByteBuffer) {
        this.uploadData(GL_RGBA8, width, height, GL_RGBA, data)
    }

    fun uploadData(
        internalFormat: Int,
        width: Int,
        height: Int,
        format: Int,
        data: ByteBuffer
    ) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

    fun delete() {
        glDeleteTextures(id)
    }

    companion object {

        fun create(
            width: Int,
            height: Int,
            data: ByteBuffer,
        ): Texture {
            val texture = Texture()
            texture.width = width
            texture.height = height

            texture.bind()

            texture.setParam(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
            texture.setParam(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
            texture.setParam(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            texture.setParam(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);
            return texture
        }


        fun load(path: String): Texture {
            val memoryStack = MemoryStack.stackPush()
            val width = memoryStack.mallocInt(1)
            val height = memoryStack.mallocInt(1)
            val components = memoryStack.mallocInt(1)

            stbi_set_flip_vertically_on_load(true)
            val image: ByteBuffer = stbi_load(path, width, height, components, 4)
                ?: throw RuntimeException("Failed to load a texture file: $path")

            return create(width.get(), height.get(), image)
        }

    }


}