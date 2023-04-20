package engine.krisp.sprite

import java.nio.file.Path

interface ResourceLoader {

    fun loadResources(path: Path)

}