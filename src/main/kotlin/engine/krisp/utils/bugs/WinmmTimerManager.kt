package engine.krisp.utils.bugs

import com.sun.jna.Native

/**
 * A Windows interrupt timer manager class.
 * This class must NOT be used on operating systems other than Windows.
 */
class WinmmTimerManager {

    private val winmm: Winmm = Native.load("winmm", Winmm::class.java)

    /**
     * Sets a minimum timer resolution (maximum delay between interrupts).
     * Run with multipliers of 50 which are less than 16 to achieve 20 tps.
     * @param uPeriod The timer resolution to set.
     */
    fun setTimerResolution(uPeriod: Int) {
       this.winmm.timeBeginPeriod(uPeriod)
    }

    /**
     * Unsets timer resolution.
     * @param uPeriod The timer resolution to unset. Must be a number which a setTimerResolution is called with before.
     */
    fun unsetTimerResolution(uPeriod: Int) {
        this.winmm.timeEndPeriod(uPeriod)
    }
}