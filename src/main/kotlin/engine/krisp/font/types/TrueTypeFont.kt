package engine.krisp.font.types

import engine.krisp.font.Font
import engine.krisp.utils.datasets.IntObject
import java.awt.FontMetrics
import java.io.FileInputStream


class TrueTypeFont(
    private val location: FileInputStream,
    private val size: Int,
    private val height: Int,
    private val antiAliasing: Boolean
) : Font {

    private val charArray = Array<IntObject?>(256) { null }
    private val customCharacters: MutableMap<Int, IntObject> = mutableMapOf()

    /** Texture used to cache the font 0-255 characters  */
    private val fontTextureID: Int? = null

    /** Default font texture width  */
    private val textureWidth = 512

    /** Default font texture height  */
    private val textureHeight = 512

    /** A reference to Java's AWT Font that we create our font texture from  */
    private val font: Font? = null

    /** The font metrics for our Java AWT font  */
    private val fontMetrics: FontMetrics? = null


    private val correctL = 9
    private var correctR = 8

}