package engine.krisp.texture

import org.lwjgl.opengl.GL30.*


class VAO {

    val id: Int = glGenVertexArrays()

    fun bind() {
        glBindVertexArray(id)
    }

    fun delete() {
        glDeleteVertexArrays(id)
    }

}