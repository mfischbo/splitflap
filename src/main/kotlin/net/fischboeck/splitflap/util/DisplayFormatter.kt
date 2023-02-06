package net.fischboeck.splitflap.util

import mu.KLogging

object DisplayFormatterBuilder {

        fun newBuilder(dimX: Short, dimY: Short, whitespace: String = " "): DisplayFormatter {
            return DisplayFormatter(dimX, dimY, whitespace)
        }

    class Insertion(val text: String, val alignment: Alignment)

    class DisplayFormatter {
        private val dimX: Short
        private val dimY: Short
        private val whitespace: String
        private val jobs: java.util.ArrayDeque<Insertion>

        internal constructor(dimX: Short, dimY: Short, whitespace: String) {
            this.dimX = dimX
            this.dimY = dimY
            this.whitespace = whitespace
            this.jobs = java.util.ArrayDeque()
        }

        fun append(text: String, alignment: Alignment): DisplayFormatter {
            if (jobs.size > 4) {
                return this;
            }
            jobs.addLast(Insertion(text, alignment))
            return this
        }

        fun build(): String {
            val buffer = StringBuilder()

            while(jobs.isNotEmpty()) {
                val current = jobs.poll()

                if (current.alignment == Alignment.LEFT) {
                    // append to buffer
                    buffer.append(padRight(current.text))
                }
            }

            while(buffer.length < dimX * dimY) {
                buffer.append(whitespace)
            }

            return buffer.toString()
        }

        private fun padRight(text: String): String {
            return if (text.length > dimX) {
                text.substring(0 until dimX)
            } else {
                val builder = StringBuilder()
                builder.append(text)
                for (i in 0 until dimX - text.length) {
                    builder.append(whitespace)
                }
                builder.toString()
            }
        }

        companion object: KLogging()

    }
}

enum class Alignment {
    LEFT, RIGHT
}