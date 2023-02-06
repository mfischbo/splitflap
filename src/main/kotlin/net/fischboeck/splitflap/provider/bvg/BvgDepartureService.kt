package net.fischboeck.splitflap.provider.bvg

import net.fischboeck.splitflap.provider.MessageProvider
import net.fischboeck.splitflap.util.Alignment
import net.fischboeck.splitflap.util.DisplayFormatterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class BvgDepartureService(
    private val bvgApiClient: BvgApiClient,

    @Value("\${provider.bvg.stopId}")
    private val stopId: String,

): MessageProvider {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH-mm")
        ?: DateTimeFormatter.ISO_LOCAL_TIME


    override fun nextMessage(): String {
        val response = bvgApiClient.getDepartures(stopId)
        val builder = DisplayFormatterBuilder.newBuilder(15,4)
        builder.append("BVG DEPARTURES", Alignment.LEFT)
        response.map {

            var dest = it.direction.uppercase()
            dest = dest.replace("Ä", "AE")
            dest = dest.replace("Ö", "OE")
            dest = dest.replace("Ü", "UE")
            if (dest.length > 6) {
                dest = dest.substring(0,6)
            }

            "${it.line.name} $dest ${formatter.format(it.plannedWhen)}"
        }.take(3)
            .forEach {
                builder.append(it, Alignment.LEFT)
            }
        return builder.build()
    }
}