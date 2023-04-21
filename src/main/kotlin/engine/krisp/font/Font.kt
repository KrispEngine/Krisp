package engine.krisp.font

import engine.krisp.texture.Texture
import org.lwjgl.system.MemoryUtil
import java.awt.Font
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Font(
    awtFont: Font = Font("Arial", Font.PLAIN, 16),
    antialiasing: Boolean = true,
) {

    private val charactersMap: MutableMap<Char, FontGlyph> = mutableMapOf()

    private var texture: Texture
    private var fontHeight: Int = 0

    init {
        texture = createFontTexture(awtFont, antialiasing)
    }


    fun getWidth(text: String): Int {
        var width = 0
        var lineWidth = 0
        for (element in text) {
            if (element == '\n') {
                width = width.coerceAtLeast(lineWidth)
                lineWidth = 0
                continue
            }
            if (element == '\r') {
                continue // Ignore carriage return
            }
            val glyph = charactersMap[element] ?: continue
            lineWidth += glyph.width
        }
        return width.coerceAtLeast(lineWidth)
    }

    fun getHeight(text: String): Int {
        var height = 0
        var lineHeight = 0

        for (element in text) {
            if (element == '\n') {
                height += lineHeight
                lineHeight = 0
                continue
            }
            if (element == '\r') {
                continue // Ignore carriage return
            }
            val glyph = charactersMap[element] ?: continue
            lineHeight = lineHeight.coerceAtLeast(glyph.height)
        }
        return height + lineHeight
    }

    fun delete() {
        texture.delete()
    }

    private fun createFontTexture(
        awtFont: Font,
        antialiasing: Boolean,
    ): Texture {
        var imageWidth = 0
        var imageHeight = 0

        for (i in 32..255) {
            if (i == 127) {
                continue // Skip DEL
            }
            val char: Char = i.toChar()
            val charImage: BufferedImage? = createCharImage(awtFont, char, antialiasing) ?: continue

            imageWidth += charImage!!.width
            imageHeight = imageHeight.coerceAtLeast(charImage.height)
        }

        fontHeight = imageHeight

        var image: BufferedImage = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
        val graphics = image.createGraphics()

        var x = 0

        /* Create image for the standard chars, again we omit ASCII 0 to 31
 * because they are just control codes */
        for (i in 32..255) {
            if (i == 127) {
                continue // Skip DEL
            }
            val char: Char = i.toChar()
            val charImage: BufferedImage? = createCharImage(awtFont, char, antialiasing) ?: continue

            val charWidth = charImage!!.width
            val charHeight = charImage.height

            val fontGlyph = FontGlyph(charWidth, charHeight, x, image.height - charHeight, 0f)
            graphics.drawImage(charImage, x, 0, null)
            x+= fontGlyph.width
            charactersMap[char] = fontGlyph
        }

        val transform = AffineTransform.getScaleInstance(1.0, -1.0)
        transform.translate(0.0, -image.height.toDouble())

        val operation = AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
        image = operation.filter(image, null)

        val width = image.width
        val height = image.height

        val pixels = IntArray(width * height)
        image.getRGB(0, 0, width, height, pixels, 0, width)

        val buffer = MemoryUtil.memAlloc(width * height * 4)
        for (i in 0 until height) {
            for (j in 0 until width) {
                val pixel = pixels[i * width + j] // Pixel as RGBA: 0xAARRGGBB
                buffer.put((pixel shr 16 and 0xFF).toByte()) // Red component 0xAARRGGBB >> 16 = 0x0000AARR
                buffer.put((pixel shr 8 and 0xFF).toByte()) // Green component 0xAARRGGBB >> 8 = 0x00AARRGG
                buffer.put((pixel and 0xFF).toByte()) // Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB
                buffer.put((pixel shr 24 and 0xFF).toByte()) // Alpha component 0xAARRGGBB >> 24 = 0xAARRGGBB
            }
        }

        buffer.flip() // Or else it breaks...

        val texture = Texture.create(width, height, buffer)
        MemoryUtil.memFree(buffer)
        return texture
    }


    private fun createCharImage(
        font: Font,
        char: Char,
        antialiasing: Boolean
    ): BufferedImage? {
        var image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        var graphics = image.createGraphics()
        if (antialiasing) {
            graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            )
        }

        graphics.font = font
        val metrics = graphics.fontMetrics
        graphics.dispose()

        val charWidth = metrics.charWidth(char)
        val charHeight = metrics.height

        if (charWidth == 0) return null

        image = BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB)
        graphics = image.createGraphics()
        if (antialiasing) {
            graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            )
        }
        graphics.font = font
        graphics.paint = java.awt.Color.WHITE
        graphics.drawString(char.toString(), 0, metrics.ascent)
        graphics.dispose()
        return image
    }

}