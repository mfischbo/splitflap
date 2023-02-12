package net.fischboeck.splitflap.util

object DisplayUtils {


    fun sanitize(text: String, maxLength: Int): String {
        var result = text.uppercase()

        // replace common characters
        result = result.replace("Ö", "OE")
        result = result.replace("Ä", "AE")
        result = result.replace("Ü", "UE")
        result = result.replace("ß", "SS")

        // substitute unknown characters
        result = result.replace(DisplayFormatterBuilder.DisplayFormatter.KNOWN_CHARS_PATTERN, " ")

        // trim to length
        if (result.length > maxLength) {
            result = result.substring(0 until maxLength)
        }
        return result
    }
}