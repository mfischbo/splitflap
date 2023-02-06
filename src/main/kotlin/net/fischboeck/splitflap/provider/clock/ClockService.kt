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
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return DisplayFormatterBuilder.newBuilder(15,4)
            .append(timezone.uppercase(), Alignment.LEFT)
            .append(DateTimeFormatter.ISO_TIME.format(now), Alignment.LEFT)
            .append("               ", Alignment.LEFT)
            .append("     ${formatter.format(now)}", Alignment.LEFT)
            .build()
    }
}