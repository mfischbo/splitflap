package net.fischboeck.splitflap.provider

interface MessageProvider {

    fun nextMessage(): String
}