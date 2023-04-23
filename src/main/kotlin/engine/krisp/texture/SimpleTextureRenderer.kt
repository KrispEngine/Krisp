package engine.krisp.texture

import engine.krisp.position.matrix.Matrix4F
import engine.krisp.utils.Color
import engine.krisp.utils.GLUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer


internal class SimpleTextureRenderer : TextureRenderer {

    private var shaderProgram: ShaderProgram? = null
    private var vao: VAO? = null
    private var vbo: VBO? = null
    private var vertices: FloatBuffer? = null
    private var numVertices: Int = 0
    private var drawing: Boolean = false


    override fun drawTextureRegion(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        s1: Float,
        t1: Float,
        s2: Float,
        t2: Float,
        color: Color
    ) {
        if (vertices!!.remaining() < 48) {
            flush() // Flush if there is not enough space for the new vertices
        }
        val r = color.red
        val g = color.green
        val b = color.blue
        val a = color.alpha

        vertices!!.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices!!.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
        vertices!!.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);

         vertices!!.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices!!.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices!!.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);

        numVertices += 6;
    }

    override fun setup() {
        if (GLUtils.isDefaultContext()) {
            vao = VAO()
            vao!!.bind()
        }
        else vao = null

        vbo = VBO()
        vbo!!.bind(GL_ARRAY_BUFFER)

        /* Create FloatBuffer */
        vertices = MemoryUtil.memAllocFloat(4096);


        /* Upload null data to allocate storage for the VBO */
        val size = (vertices!!.capacity() * java.lang.Float.BYTES).toLong()
        vbo!!.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW)

        val vertexShader: Shader?
        val fragmentShader: Shader?
        if (GLUtils.isDefaultContext()) {
            vertexShader = Shader.loadShader(GL_VERTEX_SHADER, getFile("shaders/default.vert"));
            fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, getFile("shaders/default.frag"));
        }
        else {
            vertexShader = Shader.loadShader(GL_VERTEX_SHADER, getFile("shaders/legacy.vert"));
            fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, getFile("shaders/legacy.frag"));
        }

        shaderProgram = ShaderProgram()
        shaderProgram!!.attachShader(vertexShader)
        shaderProgram!!.attachShader(fragmentShader)
        if (GLUtils.isDefaultContext()) {
            shaderProgram!!.bindFragmentDataLocation(0, "fragColor")
        }
        shaderProgram!!.link()
        shaderProgram!!.use()

        // delete shaders to free up memory
        vertexShader.delete()
        fragmentShader.delete()

        val window: Long = GLFW.glfwGetCurrentContext()
        var width: Int
        var height: Int
        MemoryStack.stackPush().use { stack ->
            val widthBuffer = stack.mallocInt(1)
            val heightBuffer = stack.mallocInt(1)
            GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer)
            width = widthBuffer.get()
            height = heightBuffer.get()
        }
        specifyVertexAttributes()

        val uniTex = shaderProgram!!.getUniformLocation("texImage")
        shaderProgram!!.setUniform(uniTex, 0)

        val model = Matrix4F()
        val uniModel = shaderProgram!!.getUniformLocation("model")
        shaderProgram!!.setUniform(uniModel, model)

        val view = Matrix4F()
        val uniView = shaderProgram!!.getUniformLocation("view")
        shaderProgram!!.setUniform(uniView, view)

        val projection = Matrix4F().orthographic(0f, width.toFloat(), height.toFloat(), 0f, -1f, 1f)
        val uniProjection = shaderProgram!!.getUniformLocation("projection")
        shaderProgram!!.setUniform(uniProjection, projection)
    }

    override fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    override fun begin() {
        check(!drawing) { "Renderer is already drawing!" }
        drawing = true
        numVertices = 0
    }

    override fun end() {
        if (!drawing) {
            throw IllegalStateException("Renderer isn't drawing!");
        }
        drawing = false;
        flush();
    }

    override fun dispose() {
        MemoryUtil.memFree(vertices)
        if (vao != null) {
            vao!!.delete()
        }
        vbo!!.delete()
        shaderProgram!!.delete()
    }


    override fun flush() {
        if (numVertices > 0) {
            vertices!!.flip()

            if (vao != null) vao!!.bind()
            else {
                vbo!!.bind(GL_ARRAY_BUFFER)
                specifyVertexAttributes()
            }

            shaderProgram!!.use()

            /* Upload the new vertex data */
            vbo!!.bind(GL_ARRAY_BUFFER);
            vbo!!.uploadSubData(GL_ARRAY_BUFFER, 0, vertices!!);

            /* Draw batch */
            glDrawArrays(GL_TRIANGLES, 0, numVertices);

            /* Clear vertex data for next batch */
            vertices!!.clear();
            numVertices = 0;
        }
    }

    /* Specify Vertex Pointers */
    private fun specifyVertexAttributes() {

        // Vertex Position
        val posAttrib: Int = shaderProgram!!.getAttributeLocation("position")
        shaderProgram!!.enableVertexAttribute(posAttrib)
        shaderProgram!!.pointVertexAttribute(posAttrib, 2, 8 * java.lang.Float.BYTES, 0)

        // Color pointer
        val colAttrib: Int = shaderProgram!!.getAttributeLocation("color")
        shaderProgram!!.enableVertexAttribute(colAttrib)
        shaderProgram!!.pointVertexAttribute(colAttrib, 4, 8 * java.lang.Float.BYTES, 2 * java.lang.Float.BYTES)

        // Texture Coordinate
        val texAttrib: Int = shaderProgram!!.getAttributeLocation("texcoord") // 1
        shaderProgram!!.enableVertexAttribute(texAttrib)
        shaderProgram!!.pointVertexAttribute(texAttrib, 2, 8 * java.lang.Float.BYTES, 6 * java.lang.Float.BYTES)

    }
}