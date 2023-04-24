package engine.krisp.sample

import engine.krisp.KrispEngine
import engine.krisp.loop.UpdateBasedGameLoop
import java.io.File


object Main {
    internal val logger = KrispEngine.newDedicatedLogger("Main")

    internal fun onEnable() {
        KrispEngine.startEngine(
            UpdateBasedGameLoop(
                SampleWindow(),
                SampleGameLogic(),
                1000L / 20L,
            )
        )
    }

    fun getSampleTextureFile(): File {
        val localPath = java.nio.file.Path.of(".")
        val fileStream = Main::class.java.classLoader.getResourceAsStream("sample_texture.png") ?: throw Exception("Could not find sample_texture.png")
        val file = File(localPath.toAbsolutePath().toString(), "sample_texture.png")
        if (file.exists()) {
            file.delete()
        }
        file.writeBytes(fileStream.readAllBytes())
        return file
     }
}

fun main() {
    Main.onEnable()
}