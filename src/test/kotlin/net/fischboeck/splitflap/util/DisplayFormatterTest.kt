package net.fischboeck.splitflap.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.exp

class DisplayFormatterTest {

    @Test
    fun `no text appended`() {

        val formatter = DisplayFormatterBuilder.newBuilder(15, 4, "_")
        val result = formatter.build()

        val expectedBuffer = StringBuffer()
        for (i in 0 until 15*4) {
            expectedBuffer.append('_')
        }
        assertEquals(expectedBuffer.toString(), result)
    }

    @Test
    fun `left aligned text is padding correctly`() {

        val result = DisplayFormatterBuilder.newBuilder(15, 4, "_")
            .append("FOOBAR")
            .build()
        val expected = "FOOBAR______________________________________________________"

        assertEquals(expected, result)
    }

    @Test
    fun `can write 4 left aligned rows correctly`() {

        val result = DisplayFormatterBuilder.newBuilder(15, 4, "_")
            .append("FOO").newLine()
            .append("BAR").newLine()
            .append("BUZZ").newLine()
            .append("BAMBOO").newLine()
            .build()
        val expected = "FOO____________BAR____________BUZZ___________BAMBOO_________"
        assertEquals(expected, result)
    }

    @Test
    fun `truncates left padded word if exceeding line`() {

        val result = DisplayFormatterBuilder.newBuilder(15, 4, "_")
            .append("FOOBARFOOBARFOOBAR", Alignment.LEFT)
            .build()
        val expected = "FOOBARFOOBARFOO_____________________________________________"
        assertEquals(expected, result)
    }

    @Test
    fun `single right aligned word`() {

        val result = DisplayFormatterBuilder.newBuilder(5, 3, "_")
            .append("XYZ", Alignment.RIGHT)
            .build()
        val expected = "__XYZ__________"

        assertEquals(expected, result)
    }

    @Test
    fun `four right aligned words`() {

        val result = DisplayFormatterBuilder.newBuilder(5, 4, "_")
            .append("XY", Alignment.RIGHT)
            .append("XY", Alignment.RIGHT)
            .append("YZ", Alignment.RIGHT)
            .append("12", Alignment.RIGHT)
            .build()

        val expected = "___XY___XY___YZ___12"
        assertEquals(expected, result)
    }

    @Test
    fun `left right left right mixed lines`() {

        val result = DisplayFormatterBuilder.newBuilder(6, 4, "_")
            .append("12").newLine()
            .append("34", Alignment.RIGHT)
            .append("56").newLine()
            .append("78", Alignment.RIGHT)
            .build()

        val expected = "12________3456________78"
        assertEquals(expected, result)
    }

    @Test
    fun `left right on same line`() {

        val result = DisplayFormatterBuilder.newBuilder(8, 2, "_")
            .append("ABC").append("ABC", Alignment.RIGHT)
            .append("CCC").append("CCC", Alignment.RIGHT)
            .build()

        val expected = "ABC__ABCCCC__CCC"
        assertEquals(expected, result)
    }

    @Test
    fun `truncates right alignment if space exceeds`() {

        val result = DisplayFormatterBuilder.newBuilder(8, 1, "_")
            .append("HELLO ").append("WORLD", Alignment.RIGHT)
            .build()

        assertEquals("HELLO WO", result)
    }

}