package engine.krisp.sprite

import java.nio.file.Path

abstract class ResourceRegistry(
    private val path: Path,
    private val loader: ResourceLoader
) {

    fun verifyIntegrity() {
        val file = path.toFile()
        if (!file.exists()){
            loader.loadResources(this.path)
        }
    }

    abstract fun getSprite(id: String): Sprite

}