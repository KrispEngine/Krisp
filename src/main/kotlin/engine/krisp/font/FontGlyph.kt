package engine.krisp.font

class FontGlyph(
    val id: Int,
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    var xOffset: Int,
    var yOffset: Int,
    var xAdvance: Int,
    var page: Int,
) {

    override fun toString(): String {
        return "FontGlyph(id=$id, x=$x, y=$y, width=$width, height=$height, xOffset=$xOffset, yOffset=$yOffset, xAdvance=$xAdvance, page=$page)"
    }

}