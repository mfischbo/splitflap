package net.fischboeck.splitflap.provider.clock

import net.fischboeck.splitflap.provider.MessageProvider
import net.fischboeck.splitflap.util.Alignment
import net.fischboeck.splitflap.util.DisplayFormatterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
class ClockService(
    @Value("\${provider.clock.timezone}")
    val timezone: String
): MessageProvider {

    override fun nextMessage(): String {
        val now = ZonedDateTime.now(ZoneId.of(timezone))

        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY")
        val timeFormatter = DateTimeFormatter.ofPattern("HH.mm")

        return DisplayFormatterBuilder.newBuilder(15,4)
            .append(timezone)
            .newLine()
            .append(timeFormatter.format(now))
            .newLine()
            .newLine()
            .append(dateFormatter.format(now), Alignment.RIGHT)
            .build()
    }
}