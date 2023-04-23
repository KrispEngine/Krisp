package engine.krisp.font

import engine.krisp.utils.Color

interface Font {

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        sizeX: Float,
        sizeY: Float,
        color: Color,
        alignment: FontAlignment
    )

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        sizeX: Float,
        sizeY: Float,
        color: Color,
    ) {
        drawString(text, x, y, sizeX, sizeY, color, FontAlignment.LEFT)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        sizeX: Float,
        sizeY: Float,
        alignment: FontAlignment
    ) {
        drawString(text, x, y, sizeX, sizeY, Color.WHITE, alignment)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        sizeX: Float,
        sizeY: Float
    ) {
        drawString(text, x, y, sizeX, sizeY, Color.WHITE, FontAlignment.LEFT)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        size: Float,
        color: Color,
        alignment: FontAlignment
    ) {
        drawString(text, x, y, size, size, color, alignment)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        size: Float,
        color: Color
    ) {
        drawString(text, x, y, size, size, color, FontAlignment.LEFT)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        size: Float,
        alignment: FontAlignment
    ) {
        drawString(text, x, y, size, size, Color.WHITE, alignment)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        size: Float
    ) {
        drawString(text, x, y, size, size, Color.WHITE, FontAlignment.LEFT)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        color: Color,
        alignment: FontAlignment
    ) {
        drawString(text, x, y, 1f, 1f, color, alignment)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        color: Color
    ) {
        drawString(text, x, y, 1f, 1f, color, FontAlignment.LEFT)
    }


}