package net.fischboeck.splitflap.provider

import net.fischboeck.splitflap.util.DisplayMode

interface MessageProvider {

    fun nextMessage(displayMode: DisplayMode): String
}