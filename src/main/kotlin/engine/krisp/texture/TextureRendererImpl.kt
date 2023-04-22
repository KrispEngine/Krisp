package engine.krisp.texture


import engine.krisp.font.Font
import engine.krisp.utils.GLUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer


class TextureRendererImpl {

    private var vao: VAO? = null
    private var vbo: VBO
    private var vertices: FloatBuffer
    private var numVertices: Int = 0
    private var drawing: Boolean = false
    private var font: Font? = null
    private var shaderProgram: ShaderProgram? = null

    init {
        if (GLUtils.isDefaultContext()) {
            vao = VAO()
            vao!!.bind()
        }
        else vao = null

        vbo = VBO()
        vbo.bind(GL_ARRAY_BUFFER)

        /* Create FloatBuffer */
        vertices = MemoryUtil.memAllocFloat(4096);


        /* Upload null data to allocate storage for the VBO */
        val size = (vertices.capacity() * java.lang.Float.BYTES).toLong()
        vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW)

    }

    /**
     * Clears the drawing area.
     */
    fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    /**
     * Begin rendering.
     */
    fun begin() {
        check(!drawing) { "Renderer is already drawing!" }
        drawing = true
        numVertices = 0
    }

    fun end() {
        if (!drawing) {
            throw IllegalStateException("Renderer isn't drawing!");
        }
        drawing = false;
        flush();
    }

    fun flush() {
        if (numVertices > 0) {
            vertices!!.flip()

            if (vao != null) vao!!.bind()
            else {
                vbo.bind(GL_ARRAY_BUFFER)
                specifyVertexAttributes()
            }

            shaderProgram!!.use()

            /* Upload the new vertex data */
            vbo.bind(GL_ARRAY_BUFFER);
            vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices!!);

            /* Draw batch */
            glDrawArrays(GL_TRIANGLES, 0, numVertices);

            /* Clear vertex data for next batch */
            vertices!!.clear();
            numVertices = 0;
        }
    }

    fun specifyVertexAttributes() {
        //TODO: specify vertex attributes
    }


}