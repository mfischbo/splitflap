package net.fischboeck.splitflap.util

enum class DisplayMode(val dimX: Int, val dimY: Int) {

    SMALL(15, 4),
    MEDIUM(24, 6)


}

object ModeSelector {

    fun of(mode: Int): DisplayMode {
        return when (mode) {
            0 -> DisplayMode.SMALL
            1 -> DisplayMode.MEDIUM
            else -> DisplayMode.MEDIUM
        }
    }
}