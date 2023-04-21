package engine.krisp.font

class FontRenderer(
    font: Font
) {

    private var drawing: Boolean = false
    private var vertices: FloatArray = FloatArray(0)
    private var numVertices: Int = 0

    fun begin() {
        if (drawing) {
            throw IllegalStateException("Cannot begin drawing while already drawing")
        }
        drawing = true
        numVertices = 0
    }

    fun end() {
        if (!drawing) {
            throw IllegalStateException("Cannot end drawing while not drawing")
        }
        drawing = false
        flush()
    }

    fun flush() {
        if (numVertices > 0) {
            
        }
    }


}