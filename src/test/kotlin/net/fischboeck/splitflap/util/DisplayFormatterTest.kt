package net.fischboeck.splitflap.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DisplayFormatterTest {

    @Test
    fun `no text appended`() {

        val formatter = DisplayFormatterBuilder.newBuilder(15, 4)
        val result = formatter.build()

        val expectedBuffer = StringBuffer()
        for (i in 0 until 15*4) {
            expectedBuffer.append('_')
        }
        assertEquals(expectedBuffer.toString(), result)
    }

    @Test
    fun `left aligned text is padding correctly`() {

        val result = DisplayFormatterBuilder.newBuilder(15, 4)
            .append("FOOBAR", Alignment.LEFT)
            .build()
        val expected = "FOOBAR______________________________________________________"

        assertEquals(expected, result)
    }

    @Test
    fun `can write 4 left aligned rows correctly`() {

        val result = DisplayFormatterBuilder.newBuilder(15, 4)
            .append("FOO", Alignment.LEFT)
            .append("BAR", Alignment.LEFT)
            .append("BUZZ", Alignment.LEFT)
            .append("BAMBOO", Alignment.LEFT)
            .build()
        val expected = "FOO____________BAR____________BUZZ___________BAMBOO_________"
        assertEquals(expected, result)
    }

    @Test
    fun `truncates left padded word if exceeding line`() {

        val result = DisplayFormatterBuilder.newBuilder(15, 4)
            .append("FOOBARFOOBARFOOBAR", Alignment.LEFT)
            .build()
        val expected = "FOOBARFOOBARFOO_____________________________________________"
        assertEquals(expected, result)
    }
}