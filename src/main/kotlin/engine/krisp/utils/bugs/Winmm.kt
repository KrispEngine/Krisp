package engine.krisp.utils.bugs

import com.sun.jna.Library

/**
 * An interface to interact with the Winmm.dll Windows library.
 */
interface Winmm : Library {

    fun timeBeginPeriod(uPeriod: Int): Int

    fun timeEndPeriod(uPeriod: Int): Int

}