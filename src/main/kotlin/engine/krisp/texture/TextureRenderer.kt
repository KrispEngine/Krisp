package engine.krisp.texture

import engine.krisp.utils.Color
import java.io.FileInputStream
import java.io.InputStream

interface TextureRenderer {

    companion object {
        fun newRenderer(): TextureRenderer {
            return SimpleTextureRenderer()
        }
    }

    /**
     * Set up the renderer, this should be called before any other methods, preferably as soon as the renderer is created.
     */
    fun setup()

    /**
     * Begin rendering.
     */
    fun begin()

    /**
     * Clear the drawing area.
     */
    fun clear()

    /**
     * End rendering.
     */
    fun end()

    /**
     * Flushes the renderer.
     */
    fun flush()

    /**
     * Dispose of the renderer and clean up its used data.
     */
    fun dispose()

    /**
     * Draw the currently bound texture to the screen at the specified position.
     * @param x1 The x position of the top left corner.
     * @param y1 The y position of the top left corner.
     * @param x2 The x position of the bottom right corner.
     * @param y2 The y position of the bottom right corner.
     * @param s1 The x position of the top left corner of the texture.
     * @param t1 The y position of the top left corner of the texture.
     * @param s2 The x position of the bottom right corner of the texture.
     * @param t2 The y position of the bottom right corner of the texture.
     * @param color The color to draw the texture with.
     */
    fun drawTextureRegion(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        s1: Float,
        t1: Float,
        s2: Float,
        t2: Float,
        color: Color
    )

    /**
     * Draw the currently bound texture to the screen at the specified position.
     * @param x1 The x position of the top left corner.
     * @param y1 The y position of the top left corner.
     * @param x2 The x position of the bottom right corner.
     * @param y2 The y position of the bottom right corner.
     * @param s1 The x position of the top left corner of the texture.
     * @param t1 The y position of the top left corner of the texture.
     * @param s2 The x position of the bottom right corner of the texture.
     * @param t2 The y position of the bottom right corner of the texture.
     */
    fun drawTextureRegion(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        s1: Float,
        t1: Float,
        s2: Float,
        t2: Float
    ) {
        drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, Color.WHITE)
    }

    fun getResource(
        path: String,
        classLoader: ClassLoader = TextureRenderer::class.java.classLoader
    ): InputStream {
        return classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
    }

    fun getFile(
        path: String,
        classLoader: ClassLoader = TextureRenderer::class.java.classLoader
    ): FileInputStream {
        val file = classLoader.getResource(path)?.file
            ?: throw IllegalArgumentException("Resource not found: $path")
        return FileInputStream(file)
    }

}