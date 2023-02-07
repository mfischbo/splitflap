package net.fischboeck.splitflap.provider.bvg

import com.github.benmanes.caffeine.cache.Cache
import mu.KLogging
import net.fischboeck.splitflap.provider.MessageProvider
import net.fischboeck.splitflap.util.Alignment
import net.fischboeck.splitflap.util.DisplayFormatterBuilder
import net.fischboeck.splitflap.util.DisplayFormatterBuilder.DisplayFormatter.Companion.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class BvgDepartureService(
    private val bvgApiClient: BvgApiClient,
    private val bvgResponseCache: Cache<String, List<BvgDeparturesResponse>>,

    @Value("\${provider.bvg.stopId}")
    private val stopId: String,

): MessageProvider {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH.mm")
        ?: DateTimeFormatter.ISO_TIME


    override fun nextMessage(): String {

        val response = bvgResponseCache.get(stopId) {
            bvgApiClient.getDepartures(stopId)
                .sortedBy { it.departureAt }
        }

        return toMessage(response)
    }

    private fun toMessage(response: List<BvgDeparturesResponse>): String {

        val builder = DisplayFormatterBuilder.newBuilder(15,4)
        builder
            .append("BVG DEPARTURES")
            .newLine()

        response.forEach {

            val departure = it.departureAt
                .withZoneSameInstant(ZoneId.of("Europe/Berlin"))

            logger.info { "${it.line.name} ${it.direction} ${departure}" }
            var dest = it.direction
            if (dest.length > 6) {
                dest = dest.substring(0,5) + "."
            }
            builder
                .append("${it.line.name} $dest")
                .append(formatter.format(departure), Alignment.RIGHT)
        }
        return builder.build()
    }

    companion object: KLogging()
}