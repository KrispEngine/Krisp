package engine.krisp.utils

object OperatingSystem {

    fun getOS(): Type {
        val os = System.getProperty("os.name").toLowerCase()
        return when {
            os.contains("win") -> Type.WINDOWS
            os.contains("nix") || os.contains("nux") || os.contains("aix") -> Type.LINUX
            os.contains("mac") -> Type.MACOS
            os.contains("sunos") -> Type.SOLARIS
            else -> Type.UNKNOWN
        }
    }

    fun isWindows(): Boolean = getOS() == Type.WINDOWS
    fun isLinux(): Boolean = getOS() == Type.LINUX
    fun isMacOS(): Boolean = getOS() == Type.MACOS
    fun isSolaris(): Boolean = getOS() == Type.SOLARIS
    fun isUnknown(): Boolean = getOS() == Type.UNKNOWN

    fun is64Bit(): Boolean = System.getProperty("os.arch").contains("64")
    fun getVersion(): String = System.getProperty("os.version")

    enum class Type {
        WINDOWS,
        LINUX,
        MACOS,
        SOLARIS,
        UNKNOWN
    }

}