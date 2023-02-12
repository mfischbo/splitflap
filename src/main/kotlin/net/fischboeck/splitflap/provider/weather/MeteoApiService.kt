package net.fischboeck.splitflap.provider.weather

import net.fischboeck.splitflap.provider.MessageProvider
import net.fischboeck.splitflap.util.Alignment
import net.fischboeck.splitflap.util.Alignment.*
import net.fischboeck.splitflap.util.DisplayFormatterBuilder
import net.fischboeck.splitflap.util.DisplayMode
import net.fischboeck.splitflap.util.DisplayMode.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Service
class MeteoApiService(
    @Value("\${provider.weather.city}")
    private val city: String,

    @Value("\${provider.weather.timezone}")
    private val timezone: String,

    @Value("\${provider.weather.latitude}")
    private val latitude: Double,

    @Value("\${provider.weather.longitude}")
    private val longitude: Double,

    private val apiClient: MeteoApiClient,


): MessageProvider {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        ?: DateTimeFormatter.ISO_TIME

    override fun nextMessage(displayMode: DisplayMode): String {

        val response = apiClient.getCurrentWeather(latitude, longitude, timezone)
        return when (displayMode) {
            SMALL -> small(response)
            MEDIUM -> medium(response)
        }
    }

    private fun small(response: MeteoResponse): String {
        return DisplayFormatterBuilder.newBuilder(SMALL.dimX, SMALL.dimY)

            // 1
            .append(city.uppercase()).newLine()

            // 2
            .newLine()

            // 3
            .append("${response.currentWeather.temperature}*C", RIGHT)

            // 4
            .append(WMODecoder.decode(response.currentWeather.weathercode), RIGHT)
            .build()
    }


    private fun medium(response: MeteoResponse): String {

        val sunrise = LocalDateTime.parse(response.dailyWeather.sunrise.first())
        val sup = formatter.format(sunrise);

        val sunset = LocalDateTime.parse(response.dailyWeather.sunset.first())
        val sdown = formatter.format(sunset)

        return DisplayFormatterBuilder.newBuilder(MEDIUM.dimX, MEDIUM.dimY)
            // 1
            .append("WEATHER $city").newLine()
            .newLine()

            //2
            .append("CURRENTLY:")
            .append("${response.currentWeather.temperature}*C", RIGHT)

            // 3
            .append("MIN: ")
            .append("${response.dailyWeather.tempMin.first()}*C")
            .append("MAX: ${response.dailyWeather.tempMax.first()}*C", RIGHT)

            // 4
            .append("RISE:").append(sup)
            .append("SET: $sdown", RIGHT)

            // 6
            .newLine()
            .append("${WMODecoder.decode(response.currentWeather.weathercode)} ", RIGHT)
            .build()
    }
}