package net.fischboeck.splitflap.util

import org.springframework.stereotype.Component
import java.lang.StringBuilder

@Component
class DebugFormatter {

    fun format(text: String, displayMode: DisplayMode): String {

        val builder = StringBuilder()
        builder.append("+")
        (0 until displayMode.dimX).forEach { _ -> builder.append("-") }
        builder.append("+\n")

        text.chunked(displayMode.dimX).forEach {
            builder.append("|")
            builder.append(it)
            builder.append("|")
            builder.append("\n")
        }

        builder.append("+")
        (0 until displayMode.dimX).forEach { _ -> builder.append("-") }
        builder.append("+\n")

        return builder.toString()
    }
}