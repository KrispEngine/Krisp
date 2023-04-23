package engine.krisp.texture

import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL20.*
import java.io.FileInputStream


class Shader(
    type: Int
) {

    companion object {

        fun createShader(
            type: Int, source: CharSequence
        ): Shader {
            val shader = Shader(type)
            shader.source(source)
            shader.compile()
            return shader
        }

        fun loadShader(
            type: Int, stream: FileInputStream
        ): Shader {
            val sb = StringBuilder()
            val reader = stream.bufferedReader()

            var line: String
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append('\n')
            }

            val source = sb.toString()
            return createShader(type, source)
        }

    }

    val id: Int = glCreateShader(type)

    fun source(source: CharSequence) {
        glShaderSource(id, source)
    }

    fun compile() {
        glCompileShader(id)
        checkStatus()
    }

    fun checkStatus() {
        val status = glGetShaderi(id, GL_COMPILE_STATUS)
        if (status != GL_TRUE) {
            throw RuntimeException(glGetShaderInfoLog(id));
        }
    }

    fun delete() {
        glDeleteShader(id)
    }


}