package engine.krisp.texture

import engine.krisp.utils.Color

interface TextureRenderer {

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
     * Draws a texture.
     */
    fun draw(texture: Texture, x: Float, y: Float, width: Float, height: Float, color: Color, rotation: Float)

    fun draw(texture: Texture, x: Float, y: Float, width: Float, height: Float, color: Color) {
        draw(texture, x, y, width, height, color, 0f)
    }

    fun draw(texture: Texture, x: Float, y: Float, width: Float, height: Float) {
        draw(texture, x, y, width, height, Color.WHITE, 0f)
    }

    fun draw(texture: Texture, x: Float, y: Float, width: Float, height: Float, rotation: Float) {
        draw(texture, x, y, width, height, Color.WHITE, rotation)
    }

}