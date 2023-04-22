package engine.krisp.texture

import java.nio.FloatBuffer
import java.nio.IntBuffer
import org.lwjgl.opengl.GL15.*


class VBO {

    val id: Int = glGenBuffers()

    fun bind(target: Int) {
        glBindBuffer(target, id)
    }

    fun uploadData(target: Int, size: Long, usage: Int) {
        glBufferData(target, size, usage)
    }

    fun uploadSubData(target: Int, offset: Long, data: FloatBuffer) {
        glBufferSubData(target, offset, data)
    }

    fun uploadData(target: Int, data: IntBuffer, usage: Int) {
        glBufferData(target, data, usage)
    }

    fun delete() {
        glDeleteBuffers(id)
    }

}