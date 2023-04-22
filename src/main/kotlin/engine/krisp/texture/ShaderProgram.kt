package engine.krisp.texture

import engine.krisp.position.Vector2F
import engine.krisp.position.Vector3F
import engine.krisp.position.Vector4F
import engine.krisp.position.matrix.Matrix2F
import engine.krisp.position.matrix.Matrix3F
import engine.krisp.position.matrix.Matrix4F
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindFragDataLocation
import org.lwjgl.system.MemoryStack

class ShaderProgram {

    val id: Int = glCreateProgram()

    fun attachShader(shader: Shader) {
        glAttachShader(id, shader.id)
    }

    fun bindFragmentDataLocation(number: Int, name: CharSequence) {
        glBindFragDataLocation(id, number, name)
    }

    fun link() {
        glLinkProgram(id)
        checkStatus()
    }

    fun getAttributeLocation(name: CharSequence): Int {
        return glGetAttribLocation(id, name)
    }

    fun enableVertexAttribute(location: Int) {
        glEnableVertexAttribArray(location)
    }

    fun disableVertexAttribute(location: Int) {
        glDisableVertexAttribArray(location)
    }

    fun pointVertexAttribute(location: Int, size: Int, stride: Int, offset: Int) {
        glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset.toLong())
    }

    fun use() {
        glUseProgram(id)
    }

    fun delete() {
        glDeleteProgram(id)
    }


    fun getUniformLocation(name: CharSequence): Int {
        return glGetUniformLocation(id, name)
    }

    fun setUniform(location: Int, value: Int) {
        glUniform1i(location, value)
    }

    fun setUniform(location: Int, vector: Vector2F) {
        MemoryStack.stackPush().use { stack ->
            val buffer = stack.mallocFloat(2)
            vector.storeInBuffer(buffer)
            glUniform2fv(location, buffer)
        }
    }

    fun setUniform(location: Int, vector: Vector3F) {
        MemoryStack.stackPush().use {
            val buffer = it.mallocFloat(3)
            vector.storeInBuffer(buffer)
            glUniform3fv(location, buffer)
        }
    }

    fun setUniform(location: Int, vector: Vector4F) {
        MemoryStack.stackPush().use {
            val buffer = it.mallocFloat(4)
            vector.storeInBuffer(buffer)
            glUniform4fv(location, buffer)
        }
    }

    fun setUniform(location: Int, matrix: Matrix2F) {
        MemoryStack.stackPush().use {
            val buffer = it.mallocFloat(2 * 2)
            matrix.storeInBuffer(buffer)
            glUniformMatrix2fv(location, false, buffer)
        }
    }

    fun setUniform(location: Int, matrix: Matrix3F) {
        MemoryStack.stackPush().use {
            val buffer = it.mallocFloat(3 * 3)
            matrix.storeInBuffer(buffer)
            glUniformMatrix3fv(location, false, buffer)
        }
    }

    fun setUniform(location: Int, matrix: Matrix4F) {
        MemoryStack.stackPush().use {
            val buffer = it.mallocFloat(4 * 4)
            matrix.storeInBuffer(buffer)
            glUniformMatrix4fv(location, false, buffer)
        }
    }

    fun checkStatus() {
        val status = glGetProgrami(id, GL_LINK_STATUS)
        if (status != GL_TRUE) {
            throw RuntimeException(glGetProgramInfoLog(id));
        }
    }


}