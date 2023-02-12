package net.fischboeck.splitflap.provider.bvg

import com.github.benmanes.caffeine.cache.Cache
import mu.KLogging
import net.fischboeck.splitflap.provider.MessageProvider
import net.fischboeck.splitflap.util.Alignment
import net.fischboeck.splitflap.util.DisplayFormatterBuilder
import net.fischboeck.splitflap.util.DisplayMode
import net.fischboeck.splitflap.util.DisplayUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
class BvgDepartureService(
    private val bvgApiClient: BvgApiClient,
    private val bvgResponseCache: Cache<String, List<BvgDeparturesResponse>>,

    @Value("\${provider.bvg.stopId}")
    private val stopId: String,

): MessageProvider {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        ?: DateTimeFormatter.ISO_TIME

    private val timeZone = ZoneId.of("Europe/Berlin")


    override fun nextMessage(displayMode: DisplayMode): String {

        val response = bvgResponseCache.get(stopId) {
            bvgApiClient.getDepartures(stopId)
                .sortedBy { it.departureAt }
        }

         if (displayMode == DisplayMode.SMALL) {
            return small(response)
        } else {
            return medium(response)
         }
    }

    private fun small(response: List<BvgDeparturesResponse>): String {

        val builder = DisplayFormatterBuilder.newBuilder(DisplayMode.SMALL.dimX,DisplayMode.SMALL.dimY)
        builder
            .append("BVG DEPARTURES")
            .newLine()
            .newLine()

        response.forEach {

            val departure = it.departureAt
                .withZoneSameInstant(timeZone)

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

    private fun medium(response: List<BvgDeparturesResponse>): String {

        val builder = DisplayFormatterBuilder.newBuilder(DisplayMode.MEDIUM.dimX, DisplayMode.MEDIUM.dimY)
        builder.append("BVG ABFAHRT")
        builder.append(formatter.format(ZonedDateTime.now().withZoneSameInstant(timeZone)), Alignment.RIGHT)
        builder.append("------------------------")

        response.forEach {

            val departure = it.departureAt.withZoneSameInstant(timeZone)
            val destination = DisplayUtils.sanitize(it.direction, 14)
            builder
                .append("${it.line.name} ")
                .append("$destination ")
                .append(formatter.format(departure.withZoneSameInstant(timeZone)), Alignment.RIGHT)
        }
        return builder.build()
    }

    companion object: KLogging()
}