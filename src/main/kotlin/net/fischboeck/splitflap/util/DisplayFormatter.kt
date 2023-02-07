package net.fischboeck.splitflap.util

import mu.KLogging
import net.fischboeck.splitflap.util.Alignment.LEFT
import net.fischboeck.splitflap.util.Alignment.RIGHT
import java.util.ArrayDeque

object DisplayFormatterBuilder {

        fun newBuilder(dimX: Short, dimY: Short, whitespace: String = " "): DisplayFormatter {
            return DisplayFormatter(dimX, dimY, whitespace)
        }

    class Insertion(val text: String, val alignment: Alignment)

    class DisplayFormatter internal constructor(private val dimX: Short, private val dimY: Short, private val whitespace: String) {

        private val lines = (0 until dimY).map { ArrayDeque <Insertion>() }.toList()
        private var current = 0;

        fun newLine(): DisplayFormatter {
            current++
            return this
        }

        fun append(text: String, alignment: Alignment = LEFT): DisplayFormatter {

            if (current > lines.size) {
                return this
            }
            val sanitizedText = sanitize(text)
            lines[current].addLast(Insertion(sanitizedText, alignment))

            // ensures text with alignment.RIGHT is the last one on this line
            // this way it's easier when collapsing
            if (alignment == RIGHT) {
                current++
            }
            return this
        }

        fun build(): String {
            val buffer = StringBuilder()
            lines.map {
                collapse(it)
            }.forEach {
                if (it.length > dimX) {
                    buffer.append(it.substring(0 until dimX))
                } else {
                    buffer.append(it)
                }
            }
            return buffer.toString()
        }

        private fun collapse(insertions: ArrayDeque<Insertion>): String {

            var result = ""
            while (insertions.isNotEmpty()) {

                val item = insertions.poll()
                val next = insertions.peek()

                if (next != null && next.alignment == LEFT) {
                    result += item.text
                }

                if (next != null && next.alignment == RIGHT) {

                    result += item.text

                    // pad until result.length + next.text.length = dimX -1
                    val pad = dimX - (result.length + next.text.length)
                    (0 until pad).forEach { _ -> result += whitespace }
                    result += next.text
                    return result
                }

                if (next == null && item.alignment == LEFT) {
                    result += item.text
                    return padRight(result)
                }

                if (next == null && item.alignment == RIGHT) {
                    val padLeft = dimX - item.text.length
                    (0 until padLeft).forEach { _ -> result += whitespace }
                    result += item.text
                }
            }

            return padRight(result)
        }


        private fun padRight(text: String): String {

            val builder = StringBuilder()
            builder.append(text)
            for (i in 0 until dimX - text.length) {
                builder.append(whitespace)
            }
            return builder.toString()
        }


        private fun sanitize(text: String): String {

            var result = text.uppercase()

            // replace common characters
            result = result.replace("Ö", "OE")
            result = result.replace("Ä", "AE")
            result = result.replace("Ü", "UE")
            result = result.replace("ß", "SS")

            // substitute unknown characters
            result = result.replace(KNOWN_CHARS_PATTERN, " ")

            // trim to length
            if (result.length > dimX) {
                result = result.substring(0 until dimX.toInt())
            }
            return result
        }

        companion object: KLogging() {
            val KNOWN_CHARS_PATTERN = Regex("[^A-Z0-9!.$%+\\-()#]")
        }
    }
}

enum class Alignment {
    LEFT, RIGHT
}