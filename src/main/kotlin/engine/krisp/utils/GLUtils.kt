package engine.krisp.utils

import org.lwjgl.opengl.GL

object GLUtils {


    /**
     * Checks if the current OpenGL context is the default one and supports OpenGL 3.2 or higher.
     */
    fun isDefaultContext(): Boolean {
        return GL.getCapabilities().OpenGL32
    }


}