package engine.krisp.utils

import java.util.concurrent.ThreadLocalRandom

class Color(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float = 1.0f,
) {

    constructor(red: Int, green: Int, blue: Int, alpha: Int = 255) : this(
        red.toFloat() / 255.0f,
        green.toFloat() / 255.0f,
        blue.toFloat() / 255.0f,
        alpha.toFloat() / 255.0f
    )

    constructor(number: Int) : this(
        (number shr 16) and 0xFF,
        (number shr 8) and 0xFF,
        number and 0xFF,
        (number shr 24) and 0xFF
    )

    fun withRed(red: Float) = Color(red, green, blue, alpha)
    fun withGreen(green: Float) = Color(red, green, blue, alpha)
    fun withBlue(blue: Float) = Color(red, green, blue, alpha)
    fun withAlpha(alpha: Float) = Color(red, green, blue, alpha)

    fun extractRed() = (red * 255.0f).toInt()
    fun extractGreen() = (green * 255.0f).toInt()
    fun extractBlue() = (blue * 255.0f).toInt()
    fun extractAlpha() = (alpha * 255.0f).toInt()

    fun addRed(red: Float) = Color(this.red + red, green, blue, alpha)
    fun addGreen(green: Float) = Color(red, this.green + green, blue, alpha)
    fun addBlue(blue: Float) = Color(red, green, this.blue + blue, alpha)
    fun addAlpha(alpha: Float) = Color(red, green, blue, this.alpha + alpha)

    fun subtractRed(red: Float) = Color(this.red - red, green, blue, alpha)
    fun subtractGreen(green: Float) = Color(red, this.green - green, blue, alpha)
    fun subtractBlue(blue: Float) = Color(red, green, this.blue - blue, alpha)
    fun subtractAlpha(alpha: Float) = Color(red, green, blue, this.alpha - alpha)

    companion object {
        fun fromHex(hex: String): Color {
            val red = hex.substring(0, 2).toInt(16)
            val green = hex.substring(2, 4).toInt(16)
            val blue = hex.substring(4, 6).toInt(16)
            val alpha = if (hex.length == 8) hex.substring(6, 8).toInt(16) else 255
            return Color(red, green, blue, alpha)
        }

        fun randomColor(): Color {
            val tlr = ThreadLocalRandom.current()
            return Color(tlr.nextInt(0, 255), tlr.nextInt(0, 255), tlr.nextInt(0, 255))
        }

        val BLACK = Color(0, 0, 0)
        val WHITE = Color(255, 255, 255)
        val RED = Color(255, 0, 0)
        val GREEN = Color(0, 255, 0)
        val BLUE = Color(0, 0, 255)
        val YELLOW = Color(255, 255, 0)
        val CYAN = Color(0, 255, 255)
        val MAGENTA = Color(255, 0, 255)
        val GRAY = Color(128, 128, 128)
        val LIGHT_GRAY = Color(192, 192, 192)
        val DARK_GRAY = Color(64, 64, 64)
        val PINK = Color(255, 175, 175)
        val ORANGE = Color(255, 200, 0)
    }

}